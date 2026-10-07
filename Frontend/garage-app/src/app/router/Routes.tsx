import { createBrowserRouter } from "react-router-dom";
import App from "../layout/App";
import PublicLayout from "../layout/PublicLayout";
import RequireAuth from "./RequireAuth";
import DashboardRedirect from "./DashboardRedirect";
import NotFound from "../errors/NotFound";
import ServerError from "../errors/ServerError";
import Unauthorized from "../errors/Unauthorized";
import { Role } from "../models/enums";
import Home from "../../features/Home/Home";
import Dashboard from "../../features/Dashboard/Dashboard";
import Login from "../../features/Account/Login";
import Signup from "../../features/Account/Signup";
import VerifyEmail from "../../features/Account/VerifyEmail";
import ResendVerification from "../../features/Account/ResendVerification";
import ForgotPassword from "../../features/Account/ForgotPassword";
import ResetPassword from "../../features/Account/ResetPassword";
import SetPassword from "../../features/Account/SetPassword";
import ChangePassword from "../../features/Account/ChangePassword";
import RegisterOwner from "../../features/GarageOwner/RegisterOwner";
import AdminHome from "../../features/Admin/AdminHome";
import RegistrationDocuments from "../../features/Admin/RegistrationDocuments";
import Users from "../../features/Admin/Users";
import Services from "../../features/Admin/Services";
import ServiceOptions from "../../features/Admin/ServiceOptions";
import OwnerHome from "../../features/GarageOwner/OwnerHome";
import MyGarages from "../../features/GarageOwner/MyGarages";
import RequestGarage from "../../features/GarageOwner/RequestGarage";
import GarageTechnicians from "../../features/GarageOwner/GarageTechnicians";
import TechnicianHome from "../../features/Technician/TechnicianHome";
import ShareLocation from "../../features/Technician/ShareLocation";
import OwnerRegistrationDocuments from "../../features/GarageOwner/OwnerRegistrationDocuments";
import SubscriptionPlans from "../../features/Subscriptions/SubscriptionPlans";
import PaymentSuccess from "../../features/Subscriptions/PaymentSuccess";
import PaymentCancel from "../../features/Subscriptions/PaymentCancel";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ServerError />,
    children: [
      // ---- public ---------------------------------------------------------------------
      {
        element: <PublicLayout />,
        children: [
          { index: true, element: <Home /> },
          { path: "login", element: <Login /> },
          { path: "signup", element: <Signup /> },
          { path: "register", element: <RegisterOwner /> },
          { path: "verify-email", element: <VerifyEmail /> },
          { path: "resend-verification", element: <ResendVerification /> },
          { path: "forgot-password", element: <ForgotPassword /> },
          { path: "reset-password", element: <ResetPassword /> },
          { path: "set-password", element: <SetPassword /> },
          { path: "unauthorized", element: <Unauthorized /> },
        ],
      },

      // ---- authenticated (dashboard shell) --------------------------------------------
      {
        element: <RequireAuth />,
        children: [
          {
            element: <Dashboard />,
            children: [
              { path: "dashboard", element: <DashboardRedirect /> },
              { path: "account/change-password", element: <ChangePassword /> },

              {
                element: <RequireAuth roles={[Role.ADMIN]} />,
                children: [
                  { path: "admin/dashboard", element: <AdminHome /> },
                  { path: "admin/registration-documents", element: <RegistrationDocuments /> },
                  { path: "admin/users", element: <Users /> },
                  { path: "admin/services", element: <Services /> },
                  { path: "admin/services/:serviceId/options", element: <ServiceOptions /> },
                ],
              },
              {
                element: <RequireAuth roles={[Role.GARAGE_OWNER]} />,
                children: [
                  { path: "owner/dashboard", element: <OwnerHome /> },
                  { path: "owner/garages", element: <MyGarages /> },
                  { path: "owner/garages/request", element: <RequestGarage /> },
                  { path: "owner/garages/register-document", element: <OwnerRegistrationDocuments /> },
                  { path: "owner/garages/subscriptions", element: <SubscriptionPlans /> },
                  { path: "owner/garages/:garageId/technicians", element: <GarageTechnicians /> },
                  { path: "payment-success", element: <PaymentSuccess /> },
                  {path: "/payment-cancel",element: <PaymentCancel />,},

                ],
              },
              {
                element: <RequireAuth roles={[Role.TECHNICIAN]} />,
                children: [
                  { path: "technician/dashboard", element: <TechnicianHome /> },
                  { path: "technician/location", element: <ShareLocation /> },
                ],
              },
            ],
          },
        ],
      },

      { path: "*", element: <NotFound /> },
    ],
  },
]);
