
import React, { useState } from "react";
import {
  AppBar,
  Avatar,
  Box,
  Divider,
  Drawer,
  IconButton,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Typography,
  useMediaQuery,
  useTheme,
} from "@mui/material";

import MenuIcon from "@mui/icons-material/Menu";
import DashboardIcon from "@mui/icons-material/Dashboard";
import LogoutIcon from "@mui/icons-material/Logout";

const drawerWidth = 250;

export interface DashboardMenuItem {
  label: string;
  icon?: React.ReactNode;
  onClick?: () => void;
}

interface DashboardProps {
  children: React.ReactNode;
  menuItems?: DashboardMenuItem[];
  userName?: string;
  userRole?: string;
}

const Dashboard: React.FC<DashboardProps> = ({
  children,
  menuItems = [],
  userName = "User",
  userRole = "USER",
}) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("md"));

  const [mobileOpen, setMobileOpen] = useState(false);

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  const handleLogout = () => {
    localStorage.removeItem("token");

    window.location.href = "/login";
  };

  const drawerContent = (
    <Box sx={{ height: "100%", display: "flex", flexDirection: "column" }}>
      {/* Logo */}
      <Box
        sx={{
          height: 70,
          display: "flex",
          alignItems: "center",
          px: 3,
        }}
      >
        <Typography
          variant="h6"
        sx={{ color: "primary.main", fontWeight: "bold", }}
        >
          Car Services
        </Typography>
      </Box>

      <Divider />

      {/* User */}
      <Box
        sx={{
          px: 2,
          py: 2,
          display: "flex",
          alignItems: "center",
          gap: 1.5,
        }}
      >
        <Avatar>{userName.charAt(0).toUpperCase()}</Avatar>

        <Box>
          <Typography sx={{fontWeight: "bold", }} noWrap>
            {userName}
          </Typography>

          <Typography
            variant="caption"
            color="text.secondary"
            sx={{ textTransform: "uppercase" }}
          >
            {userRole}
          </Typography>
        </Box>
      </Box>

      <Divider />

      {/* Navigation */}
      <List sx={{ px: 1, py: 2 }}>
        <ListItemButton
          selected
          sx={{
            borderRadius: 2,
            mb: 1,
          }}
        >
          <ListItemIcon>
            <DashboardIcon />
          </ListItemIcon>

          <ListItemText primary="Dashboard" />
        </ListItemButton>

        {menuItems.map((item, index) => (
          <ListItemButton
            key={index}
            onClick={item.onClick}
            sx={{
              borderRadius: 2,
              mb: 0.5,
            }}
          >
            <ListItemIcon>
              {item.icon}
            </ListItemIcon>

            <ListItemText primary={item.label} />
          </ListItemButton>
        ))}
      </List>

      {/* Logout */}
      <Box sx={{ mt: "auto", p: 1 }}>
        <Divider sx={{ mb: 1 }} />

        <ListItemButton
          onClick={handleLogout}
          sx={{
            borderRadius: 2,
            color: "error.main",
          }}
        >
          <ListItemIcon>
            <LogoutIcon color="error" />
          </ListItemIcon>

          <ListItemText primary="Logout" />
        </ListItemButton>
      </Box>
    </Box>
  );

  return (
    <Box sx={{ display: "flex", minHeight: "100vh", bgcolor: "#f6f8fb" }}>
      {/* Mobile Header */}
      {isMobile && (
        <AppBar
          position="fixed"
          sx={{
            width: "100%",
            zIndex: theme.zIndex.drawer + 1,
          }}
        >
          <Toolbar>
            <IconButton
              color="inherit"
              edge="start"
              onClick={handleDrawerToggle}
              sx={{ mr: 2 }}
            >
              <MenuIcon />
            </IconButton>

            <Typography variant="h6">
              Car Services
            </Typography>
          </Toolbar>
        </AppBar>
      )}

      {/* Sidebar */}
      <Box
        component="nav"
        sx={{
          width: { md: drawerWidth },
          flexShrink: { md: 0 },
        }}
      >
        {isMobile ? (
          <Drawer
            variant="temporary"
            open={mobileOpen}
            onClose={handleDrawerToggle}
            ModalProps={{
              keepMounted: true,
            }}
            sx={{
              "& .MuiDrawer-paper": {
                width: drawerWidth,
                boxSizing: "border-box",
              },
            }}
          >
            {drawerContent}
          </Drawer>
        ) : (
          <Drawer
            variant="permanent"
            open
            sx={{
              "& .MuiDrawer-paper": {
                width: drawerWidth,
                boxSizing: "border-box",
              },
            }}
          >
            {drawerContent}
          </Drawer>
        )}
      </Box>

      {/* Main */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          width: {
            md: `calc(100% - ${drawerWidth}px)`,
          },
        }}
      >
        {isMobile && <Toolbar />}

        {/* Top Header */}
        <Box
          sx={{
            height: 70,
            bgcolor: "white",
            borderBottom: "1px solid #e5e7eb",
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between",
            px: { xs: 2, md: 4 },
          }}
        >
          <Box>
            <Typography variant="h5" sx={{fontWeight: "bold", }}>
              Dashboard
            </Typography>

            <Typography variant="body2" color="text.secondary">
              Manage your car services
            </Typography>
          </Box>

          <Avatar>
            {userName.charAt(0).toUpperCase()}
          </Avatar>
        </Box>

        {/* Page Content */}
        <Box
          sx={{
            p: { xs: 2, md: 4 },
          }}
        >
          {children}
        </Box>
      </Box>
    </Box>
  );
};

export default Dashboard;
