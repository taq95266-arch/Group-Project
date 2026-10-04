import { Box, Paper, Typography } from "@mui/material";









export default function RegisterDocument(){




    return(
     <Box sx={{ minHeight: "100vh", p: 3 }}>
 <Paper
        elevation={2}
        sx={{
          p: 4,
          borderRadius: 2,
          boxShadow: "0 4px 20px rgb(0, 0, 0)",
        }}
      >
      </Paper>

 <Typography
          variant="h4"
          gutterBottom
          sx={{
            mb: 4,
            color: "#000000",
            fontWeight: "bold",
            textTransform: "uppercase",
            letterSpacing: "0.5px",
            borderBottom: "2px solid #000000",
            paddingBottom: 2,
          }}
        >
         
        </Typography>







    </Box>
    )
    

}