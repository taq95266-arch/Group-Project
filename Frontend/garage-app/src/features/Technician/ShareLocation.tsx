/* eslint-disable react-hooks/immutability */
import { useEffect, useState } from "react";

import {
  Alert,
  Box,
  Button,
  Card,
  CardContent,
  Chip,
  CircularProgress,
  Container,
  Stack,
  Typography,
} from "@mui/material";

import MyLocationIcon from "@mui/icons-material/MyLocation";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import PhoneIcon from "@mui/icons-material/Phone";

import { useTranslation } from "react-i18next";

import agent from "../../app/api/agent";
import PageHeader from "../../app/components/PageHeader";
import { toAppError } from "../../app/utils/error";
import {
  notifyError,
  notifySuccess,
} from "../../app/utils/notify";

import type { TechnicianAssignment } from "../../app/models/TechnicianAssignment";

export default function ShareLocation() {
  const { t } = useTranslation();

  const [assignments, setAssignments] = useState<
    TechnicianAssignment[]
  >([]);

  const [loading, setLoading] = useState(true);

  const [locatingAssignmentId, setLocatingAssignmentId] =
    useState<number | null>(null);

  const [sendingAssignmentId, setSendingAssignmentId] =
    useState<number | null>(null);

  useEffect(() => {
    loadAssignments();
  }, []);

  const loadAssignments = async () => {
    try {
      setLoading(true);

      const response =
        await agent.Assignments.getMyAssignments();

      setAssignments(response);
    } catch (error) {
      notifyError(
        t,
        toAppError(error).message
      );
    } finally {
      setLoading(false);
    }
  };

  const shareLocation = (
    assignment: TechnicianAssignment
  ) => {
    if (!navigator.geolocation) {
      notifyError(
        t,
        "Location is not supported by your browser."
      );
      return;
    }

    setLocatingAssignmentId(
      assignment.assignmentId
    );

    navigator.geolocation.getCurrentPosition(
      async (position) => {
        const latitude =
          position.coords.latitude;

        const longitude =
          position.coords.longitude;

        setLocatingAssignmentId(null);

        try {
          setSendingAssignmentId(
            assignment.assignmentId
          );

          const response =
            await agent.Assignments.updateLocation(
              assignment.assignmentId,
              {
                latitude,
                longitude,
              }
            );

          notifySuccess(response.message);

          await loadAssignments();
        } catch (error) {
          notifyError(
            t,
            toAppError(error).message
          );
        } finally {
          setSendingAssignmentId(null);
        }
      },
      () => {
        setLocatingAssignmentId(null);

        notifyError(
          t,
          "Unable to get your location. Please allow location permission."
        );
      },
      {
        enableHighAccuracy: true,
        timeout: 15000,
        maximumAge: 0,
      }
    );
  };

  const getStatusLabel = (status: string) => {
    switch (status) {
      case "ASSIGNED":
        return "Assigned";

      case "PREPARING":
        return "Preparing";

      case "ON_THE_WAY":
        return "On the way";

      case "ARRIVED":
        return "Arrived";

      case "COMPLETED":
        return "Completed";

      case "CANCELLED":
        return "Cancelled";

      default:
        return status;
    }
  };

  const getStatusColor = (
    status: string
  ):
    | "default"
    | "primary"
    | "secondary"
    | "success"
    | "error"
    | "warning" => {
    switch (status) {
      case "ASSIGNED":
        return "primary";

      case "PREPARING":
        return "warning";

      case "ON_THE_WAY":
        return "secondary";

      case "ARRIVED":
        return "success";

      case "COMPLETED":
        return "success";

      case "CANCELLED":
        return "error";

      default:
        return "default";
    }
  };

  return (
    <Container maxWidth="lg">
      <PageHeader
        title="Share Location"
        subtitle="View your assigned tasks and share your location for each task."
      />

      <Box sx={{ mt: 3 }}>
        <Typography
          variant="h5"
          sx={{
            mb: 2,
            fontWeight: 700,
          }}
        >
          My Tasks
        </Typography>

        {loading ? (
          <Box
            sx={{
              display: "flex",
              justifyContent: "center",
              py: 6,
            }}
          >
            <CircularProgress />
          </Box>
        ) : assignments.length === 0 ? (
          <Alert severity="info">
            You currently have no assigned tasks.
          </Alert>
        ) : (
          <Stack spacing={2}>
            {assignments.map((assignment) => {
              const isLocating =
                locatingAssignmentId ===
                assignment.assignmentId;

              const isSending =
                sendingAssignmentId ===
                assignment.assignmentId;

              const isBusy =
                isLocating || isSending;

              return (
                <Card
                  key={assignment.assignmentId}
                  sx={{
                    borderRadius: 3,
                    border: "1px solid",
                    borderColor: "divider",
                    transition: "0.2s",
                    "&:hover": {
                      boxShadow: 4,
                    },
                  }}
                >
                  <CardContent>
                    {/* Header */}
                    <Box
                      sx={{
                        display: "flex",
                        flexDirection: {
                          xs: "column",
                          sm: "row",
                        },
                        justifyContent:
                          "space-between",
                        alignItems: {
                          xs: "flex-start",
                          sm: "center",
                        },
                        gap: 2,
                      }}
                    >
                      <Box>
                        <Typography
                          variant="h6"
                          sx={{
                            fontWeight: 700,
                          }}
                        >
                          Task #
                          {assignment.requestId}
                        </Typography>

                        <Typography
                          variant="body1"
                          sx={{ mt: 0.5 }}
                        >
                          {assignment.guestName}
                        </Typography>
                      </Box>

                      <Chip
                        label={getStatusLabel(
                          assignment.status
                        )}
                        color={getStatusColor(
                          assignment.status
                        )}
                      />
                    </Box>

                    {/* Customer Information */}
                    <Stack
                      spacing={1}
                      sx={{ mt: 2 }}
                    >
                      <Box
                        sx={{
                          display: "flex",
                          alignItems: "center",
                          gap: 1,
                        }}
                      >
                        <PhoneIcon fontSize="small" />

                        <Typography variant="body2">
                          {assignment.guestPhone}
                        </Typography>
                      </Box>

                      <Box
                        sx={{
                          display: "flex",
                          alignItems: "center",
                          gap: 1,
                        }}
                      >
                        <DirectionsCarIcon fontSize="small" />

                        <Typography variant="body2">
                          {assignment.carMakeModel}
                        </Typography>
                      </Box>

                      <Typography
                        variant="body2"
                        color="text.secondary"
                      >
                        Plate:{" "}
                        {assignment.carPlateNumber}
                      </Typography>
                    </Stack>

                    {/* Current Location */}
                    {assignment.currentLatitude !==
                        null &&
                      assignment.currentLongitude !==
                        null && (
                        <Box sx={{ mt: 2 }}>
                          <Alert
                            severity="success"
                            icon={<LocationOnIcon />}
                          >
                            Location is currently
                            shared.
                          </Alert>
                        </Box>
                      )}

                    {/* Share Location Button */}
                    <Box sx={{ mt: 3 }}>
                      <Button
                        variant="contained"
                        startIcon={
                          isBusy ? (
                            <CircularProgress
                              size={20}
                              color="inherit"
                            />
                          ) : (
                            <MyLocationIcon />
                          )
                        }
                        onClick={() =>
                          shareLocation(assignment)
                        }
                        disabled={
                          isBusy ||
                          assignment.status ===
                            "COMPLETED" ||
                          assignment.status ===
                            "CANCELLED"
                        }
                        fullWidth
                      >
                        {isLocating
                          ? "Getting Location..."
                          : isSending
                          ? "Sharing Location..."
                          : assignment.status ===
                              "ON_THE_WAY"
                          ? "Update Location"
                          : "Share Location"}
                      </Button>
                    </Box>
                  </CardContent>
                </Card>
              );
            })}
          </Stack>
        )}
      </Box>
    </Container>
  );
}