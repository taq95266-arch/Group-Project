import {
    AppBar,
    Box,
    Divider,
    Drawer,
    IconButton,
    List,
    ListItem,
    Switch,
    Toolbar,
    Typography,
} from "@mui/material";
import Grid from "@mui/material/Grid";
import MenuIcon from "@mui/icons-material/Menu";
import { Link, NavLink } from "react-router-dom";
import { useState } from "react";
import { useTranslation } from "react-i18next";

import { useAppSelector } from "../../store/configureStore";
import LanguageSwitcher from "../components/LanguageSwitcher";

const drawerWidth = 240;

const NavStyles = {
    color: "inherit",
    typography: "h6",
    whiteSpace: "nowrap",
    "&:hover": {
        color: "grey.500",
    },
    "&.active": {
        color: "text.secondary",
    },
};

interface Props {
    darkMode: boolean;
    handleThemeChange: () => void;
    window?: () => Window;
}

export default function Navbar({
    darkMode,
    handleThemeChange,
    window,
}: Props) {
    const { t } = useTranslation();

    const [mobileOpen, setMobileOpen] = useState(false);

    const { user } = useAppSelector((state) => state.account);

    const midLinks = [
        { title: t("nav-home"), path: "/" },
        { title: t("nav-about"), path: "/about" },
        { title: t("nav-product"), path: "/product" },
        { title: t("nav-contact"), path: "/contact" },
    ];

    const rightLinks = [
        { title: t("login"), path: "/login" },
        { title: t("register"), path: "/register" },
    ];

    const handleDrawerToggle = () => {
        setMobileOpen((prevState) => !prevState);
    };

    const drawer = (
        <Box
            onClick={handleDrawerToggle}
            sx={{
                textAlign: "center",
                backgroundColor: "#192230",
                height: "100%",
            }}
        >
            <Box
                sx={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                }}
            >
                <Typography
                    variant="h6"
                    component={NavLink}
                    to="/"
                    sx={{
                        color: "#ffcd00",
                        textDecoration: "none",
                        fontFamily: "FireSpace",
                        my: 2,
                    }}
                >
                    Chocho
                </Typography>

                <Switch
                    checked={darkMode}
                    onChange={handleThemeChange}
                />
            </Box>

            <Divider />

            <List
                sx={{
                    display: "flex",
                    flexDirection: "column",
                    color: "#ffffff",
                }}
            >
                {midLinks.map(({ title, path }) => (
                    <ListItem
                        component={NavLink}
                        to={path}
                        key={path}
                        sx={NavStyles}
                    >
                        {title.toUpperCase()}
                    </ListItem>
                ))}
            </List>
        </Box>
    );

    const container =
        window !== undefined
            ? () => window().document.body
            : undefined;

    return (
        <>
            <AppBar
                position="static"
                sx={{
                    backgroundColor: "#192230",
                }}
            >
                <Toolbar
                    sx={{
                        marginRight: 0,
                        justifyContent: "space-between",
                    }}
                >
                    {/* Mobile menu button */}
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        edge="start"
                        onClick={handleDrawerToggle}
                        sx={{
                            mr: 2,
                            display: {
                                xs: "block",
                                md: "none",
                            },
                        }}
                    >
                        <MenuIcon />
                    </IconButton>

                    {/* Main Navbar */}
                    <Grid
                        container
                        sx={{
                            width: "100%",
                            margin: 0,
                            display: "flex",
                            flexWrap: "wrap",
                            alignItems: "center",
                            justifyContent: "space-around",
                            flexDirection: "row",
                        }}
                    >
                        {/* Logo */}
                        <Grid
                            size={1}
                            sx={{
                                display: {
                                    xs: "none",
                                    sm: "none",
                                    md: "block",
                                },
                            }}
                        >
                            <Typography
                                variant="h6"
                                component={NavLink}
                                to="/"
                                sx={{
                                    color: "#ffcd00",
                                    textDecoration: "none",
                                    fontFamily: "FireSpace",
                                }}
                            >
                                Chocho
                            </Typography>
                        </Grid>

                        {/* Middle Links */}
                        <Grid
                            size={8}
                            sx={{
                                display: {
                                    xs: "none",
                                    sm: "none",
                                    md: "block",
                                },
                            }}
                        >
                            <List
                                sx={{
                                    display: "flex",
                                }}
                            >
                                {midLinks.map(({ title, path }) => (
                                    <ListItem
                                        component={NavLink}
                                        to={path}
                                        key={path}
                                        sx={NavStyles}
                                    >
                                        {title.toUpperCase()}
                                    </ListItem>
                                ))}
                            </List>
                        </Grid>

                        {/* Right Side */}
                        <Grid size={3}>
                            <Box
                                sx={{
                                    display: "flex",
                                    alignItems: "center",
                                    justifyContent: "flex-end",
                                }}
                            >
                                <LanguageSwitcher />

                                <Switch
                                    checked={darkMode}
                                    onChange={handleThemeChange}
                                    sx={{
                                        display: {
                                            xs: "none",
                                            sm: "none",
                                            md: "block",
                                        },
                                    }}
                                />

                                <IconButton
                                    component={Link}
                                    to="/basket"
                                    size="large"
                                    edge="start"
                                    color="inherit"
                                    sx={{
                                        mr: 2,
                                    }}
                                />

                                {user ? (
                                    <>
                                        {/* SignedInMenu */}
                                    </>
                                ) : (
                                    <List
                                        sx={{
                                            display: "flex",
                                        }}
                                    >
                                        {rightLinks.map(
                                            ({ title, path }) => (
                                                <ListItem
                                                    component={NavLink}
                                                    to={path}
                                                    key={path}
                                                    sx={NavStyles}
                                                    className="signIn"
                                                >
                                                    {title.toUpperCase()}
                                                </ListItem>
                                            )
                                        )}
                                    </List>
                                )}
                            </Box>
                        </Grid>
                    </Grid>
                </Toolbar>
            </AppBar>

            {/* Mobile Drawer */}
            <nav>
                <Drawer
                    container={container}
                    variant="temporary"
                    open={mobileOpen}
                    onClose={handleDrawerToggle}
                    ModalProps={{
                        keepMounted: true,
                    }}
                    sx={{
                        display: {
                            xs: "block",
                            sm: "block",
                            md: "none",
                        },
                        "& .MuiDrawer-paper": {
                            boxSizing: "border-box",
                            width: drawerWidth,
                            backgroundColor: "#192230",
                        },
                    }}
                >
                    {drawer}
                </Drawer>
            </nav>
        </>
    );
}