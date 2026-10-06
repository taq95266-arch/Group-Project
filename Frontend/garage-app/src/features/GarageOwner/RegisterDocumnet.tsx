import { Box, Container, Paper, Typography, Grid } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import { PersonAddOutlined } from "@mui/icons-material";
import { type FieldValues, useForm } from "react-hook-form";
import { useNavigate, Link } from "react-router-dom";
import { useAppDispatch } from "../../store/configureStore";
import AppTextInput from "../../app/components/AppTextInput";
import { registerOwnerAsync } from "./ownerSlice";
import { toast } from "react-toastify";
import { useTranslation } from "react-i18next";

export default function RegisterOwner() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { t } = useTranslation();

  const {
    handleSubmit,
    control,
    register,
    formState: { isSubmitting },
  } = useForm({
    mode: "onTouched",
  });

  async function submitForm(data: FieldValues) {
    try {
      const formData = new FormData();
      
      if (data.certificateFile && data.certificateFile[0]) {
        formData.append("certificateFile", data.certificateFile[0]);
      }

      Object.keys(data).forEach((key) => {
        if (key !== "certificateFile" && data[key] !== undefined && data[key] !== null) {
          formData.append(key, data[key]);
        }
      });

      await dispatch(registerOwnerAsync(formData)).unwrap();
      toast.success("Garage registered successfully!");
      navigate("/login");
    } catch (error: unknown) {
      const err = error as { error?: string; message?: string };
      toast.error(err.error || err.message || "Registration failed");
    }
  }

  return (
    <Container component={Paper} maxWidth="md" sx={{ p: 4, mt: 4, mb: 4 }}>
      <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", mb: 3 }}>
        <PersonAddOutlined sx={{ fontSize: 40, color: "secondary.main", mb: 1 }} />
        <Typography component="h1" variant="h5">
          {t('Register Garage Owner')}
        </Typography>
      </Box>

      <Box component="form" onSubmit={handleSubmit(submitForm)} noValidate sx={{ mt: 1 }}>
        <Grid container spacing={2}>
          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="fullName" label="Full Name" control={control} rules={{ required: "Full name is required" }} />
          </Grid>
          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="garageName" label="Garage Name" control={control} rules={{ required: "Garage name is required" }} />
          </Grid>

          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="email" label="Email" control={control} rules={{ required: "Email is required" }} />
          </Grid>
          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="phone" label="Phone Number" control={control} rules={{ required: "Phone is required" }} />
          </Grid>

          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="password" label="Password" type="password" control={control} rules={{ required: "Password is required" }} />
          </Grid>
          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="commercialRegisterNumber" label="Commercial Register Number" control={control} rules={{ required: "Required" }} />
          </Grid>

          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="governorate" label="Governorate" control={control} rules={{ required: "Governorate is required" }} />
          </Grid>
          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="state" label="State" control={control} rules={{ required: "State is required" }} />
          </Grid>

          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="latitude" label="Latitude" type="number" control={control} rules={{ required: "Latitude is required" }} />
          </Grid>
          <Grid size={{ xs: 12, sm: 6 }}>
            <AppTextInput name="longitude" label="Longitude" type="number" control={control} rules={{ required: "Longitude is required" }} />
          </Grid>

          <Grid size={{ xs: 12 }}>
            <AppTextInput name="mapAddress" label="Map Address (Optional)" control={control} />
          </Grid>

          <Grid size={{ xs: 12 }}>
            <Box sx={{ p: 2, border: "1px dashed grey", borderRadius: 1 }}>
              <Typography variant="body2" color="textSecondary" gutterBottom>
                Certificate File *
              </Typography>
              <input
                type="file"
                {...register("certificateFile", { required: "Certificate file is required" })}
              />
            </Box>
          </Grid>
        </Grid>

        <LoadingButton
          loading={isSubmitting}
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
        >
          {t('Register')}
        </LoadingButton>

        <Typography variant="body2" color="text.secondary" align="center">
          {"Already have an account? "}
          <Link to="/login" style={{ color: "#1976d2", textDecoration: "none" }}>
            Sign In
          </Link>
        </Typography>
      </Box>
    </Container>
  );
}