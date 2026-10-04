import { Avatar, Box, Container, Paper, Typography } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import { LockOutlined } from "@mui/icons-material";
import { type FieldValues, useForm } from "react-hook-form";
import { SignInAsync } from "./accountSlice";
import { useLocation, useNavigate, Link } from "react-router-dom";
import { useAppDispatch } from "../../store/configureStore";
import AppTextInput from "../../app/components/AppTextInput";
import { toast } from "react-toastify";
import { useTranslation } from "react-i18next";

export default function Login() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const { t } = useTranslation();


  const from = location.state?.from?.pathname || "/";

  const {
    handleSubmit,
    control,
    formState: { isSubmitting, isValid },
  } = useForm({
    mode: "onTouched",
  });

  async function submitForm(data: FieldValues) {
    try {
      await dispatch(SignInAsync(data)).unwrap();
      navigate(from, { replace: true }); 
    }catch (error: unknown) {
    const err = error as { error?: string; message?: string };
    toast.error(
        err.error || err.message || "Invalid username or password"
    );
}
  }

  return (
    <Container
      component={Paper}
      maxWidth="xs"
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        p: 4,
        marginTop: "5%",
      }}
    >
      <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
        <LockOutlined />
      </Avatar>

      <Typography component="h1" variant="h5">
        Sign In
      </Typography>

      <Box
        component="form"
        onSubmit={handleSubmit(submitForm)}
        noValidate
        sx={{ mt: 1, width: "100%" }}
      >
  
      <AppTextInput
          name="email"
          label="Email or Username"
          control={control}
          rules={{ required: "Email or username is required" }}
        />
      <AppTextInput
          name="password"
          label="Password"
          type="password"
          control={control}
          rules={{ required: "Password is required" }}
        />

        <LoadingButton
          loading={isSubmitting}
          disabled={!isValid}
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
        >
          {t('Sign In')}
        </LoadingButton>

        <Typography variant="body2" color="text.secondary" align="center">
          {"Don't have an account? "}
          <Link to="/register" style={{ color: "#1976d2", textDecoration: "none" }}>
            Register
          </Link>
        </Typography>
      </Box>
    </Container>
  );
}