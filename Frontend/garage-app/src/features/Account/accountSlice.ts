import { createAsyncThunk, createSlice, isAnyOf } from "@reduxjs/toolkit";
import agent from "../../app/api/agent";
import type { User } from "../../app/models/User";
import type{ FieldValues } from "react-hook-form";
import { toast } from "react-toastify";
interface AccountState {
  user: User | null;
  status: string;
}

const initialState: AccountState = {
  user: null,
  status: "idle",
};

const getRolesFromToken = (token?: string | null): string[] => {
  if (!token || typeof token !== "string") return [];
  try {
    const claims = JSON.parse(atob(token.split(".")[1]));
    const roles = claims?.roles || claims?.authorities || claims?.scope || claims?.role;
    if (!roles) return [];
    return typeof roles === "string" ? [roles] : roles;
  } catch (e) {
    console.error("Failed to parse JWT token:", e);
    return [];
  }
};

export const SignInAsync = createAsyncThunk<User, FieldValues>(
  "account/SignInAsync",
  async (data, thunkAPI) => {
    try {
      const user = await agent.Account.login(data);
      localStorage.setItem("user", JSON.stringify(user));
      return user;
 } catch (error: any) {
    const message =
        error.response?.data?.error ||
        error.response?.data?.message ||
        error.message ||
        "An unexpected error occurred";
    return thunkAPI.rejectWithValue({
        error: message
    });
}
}
);

export const fetchCurrentUserAsync = createAsyncThunk<User>(
  "account/fetchCurrentUser",
  async (_, thunkAPI) => {
    const userString = localStorage.getItem("user");
    if (!userString) return thunkAPI.rejectWithValue(null);

    try {
      const storedUser = JSON.parse(userString) as User;
      
      const user = await agent.Account.currentUser(); 
      
      const updatedUser = {
        ...user,
        token: user.token || storedUser.token
      };

      localStorage.setItem("user", JSON.stringify(updatedUser));
      return updatedUser;
    } catch (error: any) {
      return thunkAPI.rejectWithValue(null);
    }
  }
);

export const accountSlice = createSlice({
  name: "account",
  initialState,
  reducers: {
    signOut: (state) => {
      state.user = null;
      localStorage.removeItem("user");
    },
    setUser: (state, action) => {
      const roles = getRolesFromToken(action.payload.token);
      state.user = { ...action.payload, roles };
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchCurrentUserAsync.rejected, (state) => {
      state.user = null;
      localStorage.removeItem("user");
      toast.error("Session expired - please login again");
    });

    builder.addMatcher(
      isAnyOf(SignInAsync.fulfilled, fetchCurrentUserAsync.fulfilled),
      (state, action) => {
        const payload = action.payload as User;
        const tokenRoles = getRolesFromToken(payload.token);
        const roles = tokenRoles.length > 0 ? tokenRoles : (payload.role ? [payload.role] : []);
        state.user = { ...payload, roles }; 
        state.status = "idle";
      }
    );

    builder.addMatcher(isAnyOf(SignInAsync.rejected), (_, action) => {
      throw action.payload;
    });
  },
});

export const { signOut, setUser } = accountSlice.actions;