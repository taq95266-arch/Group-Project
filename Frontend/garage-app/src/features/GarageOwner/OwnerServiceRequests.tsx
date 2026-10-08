import { useEffect, useState } from "react";
import {
  Alert,
  Box,
  Button,
  Card,
  CardContent,
  Chip,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Divider,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  Typography,
} from "@mui/material";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import EngineeringIcon from "@mui/icons-material/Engineering";

import { useAppDispatch, useAppSelector } from "../../store/configureStore";
import {
  acceptServiceRequestAsync,
  assignTechnicianAsync,
  fetchOwnerServiceRequestsAsync,
  fetchTechniciansAsync,
} from "./serviceRequestsSlice";

export default function OwnerServiceRequests() {
  const dispatch = useAppDispatch();

  const {
    requests,
    technicians,
    status,
    techniciansStatus,
    actionStatus,
    error,
  } = useAppSelector((state) => state.serviceRequests);

  const [selectedRequestId, setSelectedRequestId] = useState<number | null>(
    null,
  );

  const [selectedTechnicianId, setSelectedTechnicianId] = useState<number | "">(
    "",
  );

  useEffect(() => {
    dispatch(fetchOwnerServiceRequestsAsync());
  }, [dispatch]);

  const selectedRequest = requests.find(
    (request) => request.requestId === selectedRequestId,
  );

  const handleAccept = async (requestId: number) => {
    await dispatch(acceptServiceRequestAsync(requestId));
  };

  const handleOpenAssign = async (requestId: number, garageId: number) => {
    setSelectedRequestId(requestId);
    setSelectedTechnicianId("");

    await dispatch(fetchTechniciansAsync(garageId));
  };

  const handleAssign = async () => {
    if (!selectedRequest || selectedTechnicianId === "") {
      return;
    }

    const result = await dispatch(
      assignTechnicianAsync({
        requestId: selectedRequest.requestId,
        technicianId: selectedTechnicianId,
      }),
    );

    if (assignTechnicianAsync.fulfilled.match(result)) {
      setSelectedRequestId(null);
      setSelectedTechnicianId("");
    }
  };

  const getStatusColor = (
    requestStatus: string,
  ): "warning" | "success" | "info" | "default" => {
    switch (requestStatus) {
      case "PENDING":
        return "warning";

      case "ACCEPTED":
        return "success";

      case "IN_PROGRESS":
        return "info";

      default:
        return "default";
    }
  };

  if (status === "loading" && requests.length === 0) {
    return (
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          py: 8,
        }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box>
      <Box sx={{ mb: 4 }}>
        <Typography
          variant='h4'
          sx={{
            fontWeight: 700,
            color: "#0f172a",
            mb: 1,
          }}>
          Service Requests
        </Typography>

        <Typography
          variant='body1'
          sx={{
            color: "#64748b",
          }}>
          Manage customer requests and assign technicians.
        </Typography>
      </Box>

      {error && (
        <Alert
          severity='error'
          sx={{
            mb: 3,
            borderRadius: 2,
          }}>
          {error}
        </Alert>
      )}

      {requests.length === 0 ? (
        <Card
          sx={{
            borderRadius: 3,
            boxShadow: "0 4px 20px rgba(15, 23, 42, 0.08)",
          }}>
          <CardContent sx={{ py: 6, textAlign: "center" }}>
            <EngineeringIcon
              sx={{
                fontSize: 50,
                color: "#94a3b8",
                mb: 2,
              }}
            />

            <Typography
              variant='h6'
              sx={{
                fontWeight: 600,
                color: "#334155",
              }}>
              No service requests
            </Typography>

            <Typography
              sx={{
                color: "#64748b",
                mt: 1,
              }}>
              Customer requests will appear here.
            </Typography>
          </CardContent>
        </Card>
      ) : (
        <Grid container spacing={3}>
          {requests.map((request) => (
            <Grid key={request.requestId} size={{ xs: 12, md: 6, lg: 4 }}>
              <Card
                sx={{
                  height: "100%",
                  borderRadius: 3,
                  boxShadow: "0 4px 20px rgba(15, 23, 42, 0.08)",
                  border: "1px solid #e2e8f0",
                }}>
                <CardContent sx={{ p: 3 }}>
                  <Box
                    sx={{
                      display: "flex",
                      justifyContent: "space-between",
                      alignItems: "center",
                      mb: 2,
                    }}>
                    <Typography
                      variant='h6'
                      sx={{
                        fontWeight: 700,
                        color: "#0f172a",
                      }}>
                      Request #{request.requestId}
                    </Typography>

                    <Chip
                      label={request.status.replace("_", " ")}
                      color={getStatusColor(request.status)}
                      size='small'
                    />
                  </Box>

                  <Divider sx={{ mb: 2 }} />

                  <Box sx={{ mb: 2 }}>
                    <Typography
                      variant='body2'
                      sx={{
                        color: "#64748b",
                        mb: 0.5,
                      }}>
                      Customer
                    </Typography>

                    <Typography
                      sx={{
                        fontWeight: 600,
                        color: "#1e293b",
                      }}>
                      {request.guestName}
                    </Typography>

                    <Typography variant='body2' sx={{ color: "#475569" }}>
                      {request.guestPhone}
                    </Typography>
                  </Box>

                  <Box sx={{ mb: 2 }}>
                    <Typography
                      variant='body2'
                      sx={{
                        color: "#64748b",
                        mb: 0.5,
                      }}>
                      Garage
                    </Typography>

                    <Typography
                      sx={{
                        fontWeight: 600,
                        color: "#1e293b",
                      }}>
                      {request.garageName}
                    </Typography>
                  </Box>

                  <Box sx={{ mb: 2 }}>
                    <Typography
                      variant='body2'
                      sx={{
                        color: "#64748b",
                        mb: 0.5,
                      }}>
                      Vehicle
                    </Typography>

                    <Typography
                      sx={{
                        fontWeight: 600,
                        color: "#1e293b",
                      }}>
                      {request.carMakeModel}
                    </Typography>

                    <Typography variant='body2' sx={{ color: "#475569" }}>
                      Plate: {request.carPlateNumber}
                    </Typography>
                  </Box>

                  <Box sx={{ mb: 2 }}>
                    <Typography
                      variant='body2'
                      sx={{
                        color: "#64748b",
                        mb: 0.5,
                      }}>
                      Price
                    </Typography>

                    <Typography
                      sx={{
                        fontWeight: 700,
                        color: "#0f172a",
                      }}>
                      {request.appliedPrice} OMR
                    </Typography>
                  </Box>

                  {request.latitude != null && request.longitude != null && (
                    <Button
                      fullWidth
                      variant='outlined'
                      startIcon={<LocationOnIcon />}
                      sx={{
                        mb: 2,
                        borderRadius: 2,
                        textTransform: "none",
                      }}
                      onClick={() => {
                        window.open(
                          `https://www.google.com/maps?q=${request.latitude},${request.longitude}`,
                          "_blank",
                        );
                      }}>
                      View Customer Location
                    </Button>
                  )}

                  {request.status === "PENDING" && (
                    <Button
                      fullWidth
                      variant='contained'
                      color='success'
                      startIcon={<CheckCircleIcon />}
                      disabled={actionStatus === "loading"}
                      onClick={() => handleAccept(request.requestId)}
                      sx={{
                        borderRadius: 2,
                        textTransform: "none",
                        fontWeight: 600,
                      }}>
                      {actionStatus === "loading"
                        ? "Accepting..."
                        : "Accept Request"}
                    </Button>
                  )}

                  {request.status === "ACCEPTED" && (
                    <Button
                      fullWidth
                      variant='contained'
                      startIcon={<EngineeringIcon />}
                      disabled={techniciansStatus === "loading"}
                      onClick={() =>
                        handleOpenAssign(request.requestId, request.garageId)
                      }
                      sx={{
                        borderRadius: 2,
                        textTransform: "none",
                        fontWeight: 600,
                      }}>
                      {techniciansStatus === "loading"
                        ? "Loading Technicians..."
                        : "Assign Technician"}
                    </Button>
                  )}

                  {request.status === "IN_PROGRESS" && (
                    <Alert
                      severity='info'
                      sx={{
                        borderRadius: 2,
                      }}>
                      Technician has been assigned and service is in progress.
                    </Alert>
                  )}
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      )}

      <Dialog
        open={selectedRequestId !== null}
        onClose={() => {
          setSelectedRequestId(null);
          setSelectedTechnicianId("");
        }}
        fullWidth
        maxWidth='sm'>
        <DialogTitle
          sx={{
            fontWeight: 700,
          }}>
          Assign Technician
        </DialogTitle>

        <DialogContent>
          <Typography
            variant='body2'
            sx={{
              color: "#64748b",
              mb: 3,
            }}>
            Select an available technician for this service request.
          </Typography>

          {techniciansStatus === "loading" ? (
            <Box
              sx={{
                display: "flex",
                justifyContent: "center",
                py: 4,
              }}>
              <CircularProgress />
            </Box>
          ) : technicians.length === 0 ? (
            <Alert severity='warning'>
              No available technicians found for this garage.
            </Alert>
          ) : (
            <FormControl fullWidth>
              <InputLabel>Technician</InputLabel>

              <Select
                value={selectedTechnicianId}
                label='Technician'
                onChange={(event) =>
                  setSelectedTechnicianId(event.target.value as number)
                }>
                {technicians
                  .filter((technician) => technician.isAvailable)
                  .map((technician) => (
                    <MenuItem key={technician.id} value={technician.id}>
                      {technician.fullName}
                      {technician.specialization
                        ? ` - ${technician.specialization}`
                        : ""}
                    </MenuItem>
                  ))}
              </Select>
            </FormControl>
          )}
        </DialogContent>

        <DialogActions sx={{ px: 3, pb: 3 }}>
          <Button
            onClick={() => {
              setSelectedRequestId(null);
              setSelectedTechnicianId("");
            }}
            sx={{
              textTransform: "none",
            }}>
            Cancel
          </Button>

          <Button
            variant='contained'
            disabled={selectedTechnicianId === "" || actionStatus === "loading"}
            onClick={handleAssign}
            sx={{
              textTransform: "none",
              borderRadius: 2,
            }}>
            {actionStatus === "loading" ? "Assigning..." : "Assign Technician"}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}
