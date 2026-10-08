import { useState } from "react";

import {
  Box,
  Button,
  Paper,
  TextField,
  Typography,
} from "@mui/material";

import CloseIcon from "@mui/icons-material/Close";

import agent from "../../app/api/agent";

interface Props {
  garageOptionId: number;
  onClose: () => void;
}

export default function CustomerServiceRequestForm({
  garageOptionId,
  onClose,
}: Props) {
  const [guestName, setGuestName] = useState("");
  const [guestEmail, setGuestEmail] = useState("");
  const [guestPhone, setGuestPhone] = useState("");
  const [carMakeModel, setCarMakeModel] = useState("");
  const [carPlateNumber, setCarPlateNumber] = useState("");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (
    event: React.FormEvent
  ) => {
    event.preventDefault();
    setError("");

    if (
      !guestName ||
      !guestEmail ||
      !guestPhone ||
      !carMakeModel ||
      !carPlateNumber
    ) {
      setError("Please fill in all fields.");
      return;
    }

    try {
      setLoading(true);

      const position = await new Promise<GeolocationPosition>(
        (resolve, reject) => {
          if (!navigator.geolocation) {
            reject(
              new Error(
                "Location is not supported by this browser."
              )
            );
            return;
          }

          navigator.geolocation.getCurrentPosition(
            resolve,
            reject,
            {
              enableHighAccuracy: true,
              timeout: 10000,
              maximumAge: 0,
            }
          );
        }
      );

      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;

      await agent.Customer.createServiceRequest({
        garageOptionId,
        guestName,
        guestEmail,
        guestPhone,
        carMakeModel,
        carPlateNumber,
        latitude,
        longitude,
      });

      alert(
        "Service request submitted successfully. You will receive a tracking link by email."
      );

      onClose();
    } catch (error) {
      if (error instanceof GeolocationPositionError) {
        if (error.code === error.PERMISSION_DENIED) {
          setError(
            "Location permission is required to submit the service request."
          );
        } else if (
          error.code === error.POSITION_UNAVAILABLE
        ) {
          setError(
            "Unable to get your current location."
          );
        } else if (error.code === error.TIMEOUT) {
          setError(
            "Getting your location took too long. Please try again."
          );
        } else {
          setError(
            "Unable to get your location."
          );
        }
      } else {
        setError(
          "Failed to submit service request. Please try again."
        );
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        position: "fixed",
        inset: 0,
        backgroundColor: "rgba(0,0,0,0.5)",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        zIndex: 1300,
        p: 2,
      }}
    >
      <Paper
        sx={{
          width: "100%",
          maxWidth: 550,
          p: { xs: 3, md: 4 },
          borderRadius: 3,
          maxHeight: "90vh",
          overflowY: "auto",
        }}
      >
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between",
            mb: 3,
          }}
        >
          <Typography
            variant="h5"
            sx={{
              fontWeight: "bold",
              color: "#0b1f3a",
            }}
          >
            Request Service
          </Typography>

          <Button
            onClick={onClose}
            sx={{
              minWidth: "auto",
              color: "#0b1f3a",
            }}
          >
            <CloseIcon />
          </Button>
        </Box>

        <Box
          component="form"
          onSubmit={handleSubmit}
          sx={{
            display: "flex",
            flexDirection: "column",
            gap: 2,
          }}
        >
          <TextField
            label="Full Name"
            value={guestName}
            onChange={(event) =>
              setGuestName(event.target.value)
            }
            fullWidth
          />

          <TextField
            label="Email Address"
            type="email"
            value={guestEmail}
            onChange={(event) =>
              setGuestEmail(event.target.value)
            }
            fullWidth
          />

          <TextField
            label="Phone Number"
            value={guestPhone}
            onChange={(event) =>
              setGuestPhone(event.target.value)
            }
            fullWidth
          />

          <TextField
            label="Car Make & Model"
            placeholder="Toyota Camry"
            value={carMakeModel}
            onChange={(event) =>
              setCarMakeModel(event.target.value)
            }
            fullWidth
          />

          <TextField
            label="Car Plate Number"
            value={carPlateNumber}
            onChange={(event) =>
              setCarPlateNumber(event.target.value)
            }
            fullWidth
          />

          <Typography
            variant="body2"
            color="text.secondary"
          >
            Your current location will be shared with the
            garage to help the technician find you.
          </Typography>

          <Typography
            variant="body2"
            color="text.secondary"
          >
            A tracking link will be sent to your email
            after a technician is assigned.
          </Typography>

          {error && (
            <Typography color="error">
              {error}
            </Typography>
          )}

          <Button
            type="submit"
            variant="contained"
            size="large"
            disabled={loading}
            sx={{
              backgroundColor: "#0b1f3a",
              py: 1.3,
              fontWeight: "bold",
              "&:hover": {
                backgroundColor: "#07152a",
              },
            }}
          >
            {loading
              ? "Getting your location..."
              : "Submit Request"}
          </Button>
        </Box>
      </Paper>
    </Box>
  );
}

