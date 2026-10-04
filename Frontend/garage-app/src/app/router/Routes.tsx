import { createBrowserRouter } from "react-router-dom";

import Login from "../../features/Account/Login";
import RequireAuth from "./RequireAuth";
import App from "../layout/App";


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
        element: <RequireAuth roles={["Admin"]} />,
        children: [
          {
            path: "dashboard",
            // element: <Dashboard />,
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