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

const getRolesFromToken = (token: string) => {
  if (!token) return [];
  try {
    const claims = JSON.parse(atob(token.split(".")[1]));
    const roles = claims.roles || claims.authorities || claims.scope;
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
    if (userString) {
      const user = JSON.parse(userString) as User;
      return user;
    }
    return thunkAPI.rejectWithValue(null);
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
        const roles = getRolesFromToken(payload.token);
        state.user = { ...action.payload, roles };
      }
    );

    builder.addMatcher(isAnyOf(SignInAsync.rejected), (_, action) => {
      throw action.payload;
    });
  },
});

export const { signOut, setUser } = accountSlice.actions;