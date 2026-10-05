
import React from "react";

import PeopleIcon from "@mui/icons-material/People";
import GarageIcon from "@mui/icons-material/Garage";
import DescriptionIcon from "@mui/icons-material/Description";
import BuildIcon from "@mui/icons-material/Build";
import type { DashboardMenuItem } from "../Dashboard/Dashbord";
import Dashboard from "../Dashboard/Dashbord";

const AdminDashboard: React.FC = () => {
  const adminMenuItems: DashboardMenuItem[] = [
    {
      label: "Users",
      icon: <PeopleIcon />,
      onClick: () => {
        console.log("Users page");
      },
    },

    {
      label: "Garages",
      icon: <GarageIcon />,
      onClick: () => {
        console.log("Garages page");
      },
    },

    {
      label: "Registration Documents",
      icon: <DescriptionIcon />,
      onClick: () => {
        console.log("Documents page");
      },
    },

    {
      label: "Services",
      icon: <BuildIcon />,
      onClick: () => {
        console.log("Services page");
      },
    },
  ];

  return (
    <Dashboard
      userName="Admin"
      userRole="ADMIN"
      menuItems={adminMenuItems}
    >
      <h1>test</h1>
      {/* <AddForm /> */}
    </Dashboard>
  );
};

export default AdminDashboard;

