import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAppSelector } from "../../store/configureStore";
import { toast } from "react-toastify";
import { useEffect } from "react";


interface Props {
    roles?: string[];
}
export default function RequireAuth({ roles }: Props) {

    const { user } = useAppSelector(state => state.account);

    const location = useLocation();

const userRoles: string[] = user?.roles 
    ? user.roles 
    : user?.role 
    ? [user.role] 
    : [];


    const hasRequiredRole = roles 
    ? roles.some((role) => userRoles.includes(role))
    : true;



    useEffect(() => {
    if (user && roles && !hasRequiredRole) {
      toast.error("Not authorised to access this area");
    }
  }, [user, roles, hasRequiredRole]);


  if (status?.includes("pending") || status === "loading") {
    return <div>Loading...</div>;
  }

    if (!user) {
        return <Navigate to='/login' state={{from: location}} />
    }

    if (roles && !roles?.some(r => user.role?.includes(r))) {
        toast.error('Not authorised to access this area');
        return <Navigate to='/login' />
    }

    return <Outlet />

}