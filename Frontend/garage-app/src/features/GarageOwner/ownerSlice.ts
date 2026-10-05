import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import agent from "../../app/api/agent";
import type { RegisterDocumnet } from "../../app/models/RegisterDocumnet";

interface OwnerState {
  status: string;
}

const initialState: OwnerState = {
  status: "idle",
};



export const registerOwnerAsync = createAsyncThunk<RegisterDocumnet, FormData>(
  "owner/registerOwnerAsync",
  async (formData, thunkAPI) => {
    try {
      const response = await agent.Owner.registerDocumnet(formData);
      return response;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      const message =
        error.response?.data?.error ||
        error.response?.data?.message ||
        error.message ||
        "An unexpected error occurred during garage registration";
      return thunkAPI.rejectWithValue({ error: message });
    }
  }
);

export const ownerSlice = createSlice({
  name: "owner",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(registerOwnerAsync.pending, (state) => {
        state.status = "registeringOwner";
      })
      .addCase(registerOwnerAsync.fulfilled, (state) => {
        state.status = "idle";
      })
      .addCase(registerOwnerAsync.rejected, (state) => {
        state.status = "idle";
      });
  },
});

export const ownerReducer = ownerSlice.reducer;