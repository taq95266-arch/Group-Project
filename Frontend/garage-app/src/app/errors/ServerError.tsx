import { Container, Divider, Paper, Typography } from "@mui/material";
import { useLocation } from "react-router-dom";




interface LocationState {
  error?: {
    title?: string;
    detail?: string;
  };
}
export default function ServerError() {
   const location = useLocation();
  const state = location.state as LocationState;

    return (

        <Container component={Paper}>
            {state?.error ? (
                <>
                    <Typography gutterBottom variant="h3" color='secondary'>
                        {state.error.title}
                    </Typography>
                    <Divider />

                    <Typography variant="body1">
                        {state.error.detail || 'Internal server error'}
                    </Typography>
                </>
            ) : (
                    <Typography gutterBottom variant='h5'>
                        Server Error
                    </Typography>
            )}
            
         

        </Container>
    )
}