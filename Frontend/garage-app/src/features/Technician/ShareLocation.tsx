import { useState } from "react";
import { Alert, Box, Container, Grid, Paper } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import MyLocationIcon from "@mui/icons-material/MyLocation";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import agent from "../../app/api/agent";
import AppTextInput from "../../app/components/AppTextInput";
import PageHeader from "../../app/components/PageHeader";
import { toAppError } from "../../app/utils/error";
import { notifyError, notifySuccess } from "../../app/utils/notify";
import { rules } from "../../app/utils/validation";

interface Form {
  assignmentId: string;
  latitude: string;
  longitude: string;
}

export default function ShareLocation() {
  const { t } = useTranslation();
  const [locating, setLocating] = useState(false);
  const { handleSubmit, control, setValue, formState: { isSubmitting } } = useForm<Form>({ mode: "onTouched" });
  const r = rules(t);

  const useMyLocation = () => {
    if (!navigator.geolocation) {
      notifyError(t, t("location.unsupported"));
      return;
    }
    setLocating(true);
    navigator.geolocation.getCurrentPosition(
      (position) => {
        setValue("latitude", position.coords.latitude.toFixed(7), { shouldValidate: true });
        setValue("longitude", position.coords.longitude.toFixed(7), { shouldValidate: true });
        setLocating(false);
      },
      () => {
        setLocating(false);
        notifyError(t, t("location.denied"));
      },
      { enableHighAccuracy: true, timeout: 15000 },
    );
  };

  async function submitForm(values: Form) {
    const assignmentId = Number(values.assignmentId);
    try {
      const response = await agent.Assignments.updateLocation(assignmentId, {
        assignmentId,
        latitude: Number(values.latitude),
        longitude: Number(values.longitude),
      });
      notifySuccess(response.message);
    } catch (error) {
      notifyError(t, toAppError(error).message);
    }
  }

  return (
    <>
      <PageHeader title={t("menu.shareLocation")} subtitle={t("location.subtitle")} />
      <Container component={Paper} maxWidth="sm" sx={{ p: 4, mx: 0 }}>
        <Alert severity="info" sx={{ mb: 2 }}>{t("location.notice")}</Alert>
        <Box component="form" onSubmit={handleSubmit(submitForm)} noValidate>
          <AppTextInput
            name="assignmentId"
            control={control}
            label={t("fields.assignmentId")}
            type="number"
            rules={r.positiveNumber}
          />
          <Grid container spacing={2}>
            <Grid size={{ xs: 12, sm: 6 }}>
              <AppTextInput name="latitude" control={control} label={t("fields.latitude")} type="number" rules={r.latitude} slotProps={{ htmlInput: { step: "any" } }} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6 }}>
              <AppTextInput name="longitude" control={control} label={t("fields.longitude")} type="number" rules={r.longitude} slotProps={{ htmlInput: { step: "any" } }} />
            </Grid>
          </Grid>
          <Box sx={{ display: "flex", gap: 2, mt: 2, flexWrap: "wrap" }}>
            <LoadingButton loading={locating} variant="outlined" startIcon={<MyLocationIcon />} onClick={useMyLocation}>
              {t("location.useMine")}
            </LoadingButton>
            <LoadingButton loading={isSubmitting} type="submit" variant="contained">
              {t("location.send")}
            </LoadingButton>
          </Box>
        </Box>
      </Container>
    </>
  );
}
