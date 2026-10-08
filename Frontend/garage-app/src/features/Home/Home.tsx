import { useEffect } from "react";
import { Box, Button, Paper, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

import {
  useAppSelector,
  useAppDispatch,
} from "../../store/configureStore";

import { homePathForRole, paths } from "../../app/utils/paths";

import ChatAssistant from "../ChatAssistant/ChatAssistant";
import CustomerServices from "../Customer/CustomerServices";

import { fetchCustomerServicesAsync } from "../Customer/customerSlice";

export default function Home() {
  const { t } = useTranslation();

  const dispatch = useAppDispatch();

  const user = useAppSelector(
    (state) => state.account.user
  );

  useEffect(() => {
    dispatch(fetchCustomerServicesAsync());
  }, [dispatch]);

  return (
    <>
      <Paper
        sx={{
          p: { xs: 3, md: 6 },
          mt: 2,
        }}
      >
        <Typography
          variant="h3"
          sx={{
            fontWeight: "bold",
            mb: 2,
          }}
        >
          {t("home.title")}
        </Typography>

        <Typography
          variant="h6"
          color="text.secondary"
          sx={{
            maxWidth: 640,
            mb: 4,
            fontWeight: 400,
          }}
        >
          {t("home.subtitle")}
        </Typography>

        <Box
          sx={{
            display: "flex",
            gap: 2,
            flexWrap: "wrap",
          }}
        >
          {user ? (
            <Button
              variant="contained"
              size="large"
              component={Link}
              to={homePathForRole(user.role)}
            >
              {t("nav.dashboard")}
            </Button>
          ) : (
            <>
              <Button
                variant="contained"
                size="large"
                color="secondary"
                component={Link}
                to={paths.registerOwner}
              >
                {t("nav.registerGarage")}
              </Button>

              <Button
                variant="outlined"
                size="large"
                component={Link}
                to={paths.login}
              >
                {t("nav.login")}
              </Button>
            </>
          )}
        </Box>
      </Paper>

      <Box sx={{ mt: 4 }}>
        <CustomerServices />
      </Box>

      <ChatAssistant />
    </>
  );
}

