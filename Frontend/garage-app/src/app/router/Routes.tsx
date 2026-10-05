import { createBrowserRouter } from "react-router-dom";

import Login from "../../features/Account/Login";
import RequireAuth from "./RequireAuth";
import App from "../layout/App";
import AdminDashboard from "../../features/Admin/AdminDashboard";


export const router = createBrowserRouter([

    {
        path: '/',
        element: <App />,
        children: [
            {
                element: <RequireAuth />, children: [

                ],
            },

          
      {
        // admin routes
        element: <RequireAuth roles={["ADMIN"]} />,
        children: [
          {
            path: "admin/dashboard",
            element: <AdminDashboard />,
            children: [

            ],
          },
        ],
      },

            // { path: '', element: <HomePage /> },
            { path: 'login', element: <Login /> }


               
            
        ]
    }
])