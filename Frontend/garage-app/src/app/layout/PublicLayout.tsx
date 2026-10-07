import { AppBar, Box, Button, Container, Toolbar, Typography } from "@mui/material";
import { Link, Outlet } from "react-router-dom";
import { useTranslation } from "react-i18next";
import LanguageSwitcher from "../components/LanguageSwitcher";
import { useAppSelector } from "../../store/configureStore";
import { homePathForRole, paths } from "../utils/paths";

export default function PublicLayout() {
  const { t } = useTranslation();
  const user = useAppSelector((state) => state.account.user);

  return (
    <Box sx={{ minHeight: "100vh", bgcolor: "background.default" }}>
      <AppBar position="static" sx={{ backgroundColor: "#192230" }}>
        <Toolbar sx={{ gap: 1 }}>
          <Typography
            variant="h6"
            component={Link}
            to={paths.home}
            sx={{ color: "#ffcd00", textDecoration: "none", fontWeight: "bold", flexGrow: 1 }}
          >
            {t("app.name")}
          </Typography>

          <LanguageSwitcher />

          {user ? (
            <Button color="inherit" component={Link} to={homePathForRole(user.role)}>
              {t("nav.dashboard")}
            </Button>
          ) : (
            <>
              <Button color="inherit" component={Link} to={paths.login}>
                {t("nav.login")}
              </Button>
              <Button color="inherit" component={Link} to={paths.signup} sx={{ display: { xs: "none", sm: "inline-flex" } }}>
                {t("nav.signup")}
              </Button>
              <Button variant="contained" color="secondary" component={Link} to={paths.registerOwner}>
                {t("nav.registerGarage")}
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ mt: 4, pb: 6 }}>
        <Outlet />
      </Container>
    </Box>
  );
}
