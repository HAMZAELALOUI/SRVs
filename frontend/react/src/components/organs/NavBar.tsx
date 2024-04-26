import React, { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { Image } from "../atoms/Image";
import { Button } from "../atoms/Button";
import Logo from "../../assets/logo1.png";
import { NavLinks, NavButtons } from "../particles/DataLists";
import { List } from "../atoms/List";
import { ArrowCircleRight, CirclesFour } from "@phosphor-icons/react";
import { Slide } from "react-awesome-reveal";
import { useAuth } from "../AuthContext";
import Dropdown from "../atoms/Dropdown.tsx";

const NavBar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [navBarColor, setNavBarColor] = useState(false);
  const email = sessionStorage.getItem("email");

  const toggleMenu = () => setOpen(!open);

  const handleScroll = () => {
    setNavBarColor(window.scrollY > 10);
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  return (
    <header className="w-full h-auto bg-transparent overflow-visible fixed top-0 left-0 z-40">
      <Slide direction="down">
        <nav
          className={`w-full md:h-24 h-20 ${
            navBarColor ? "bg-white" : "bg-transparent"
          } lg:px-24 md:px-12 px-8 flex justify-between items-center`}
        >
          <Image
            as="a"
            href="/"
            className="md:h-10 h-8"
            image={Logo}
            alt="Logo"
          />
          <div className="lg:flex hidden items-center gap-20 ">
            <ul className="flex items-center justify-center gap-8">
              {NavLinks.map((navlink, index) => (
                <List key={index} className="w-full text-base">
                  <NavLink to={navlink.url} className="nav-link-style">
                    {navlink.name}
                  </NavLink>
                </List>
              ))}
            </ul>
            {user ? (
              <Dropdown className="z-50" userName={email} logout={logout} />
            ) : (
              <ul className="flex items-center justify-center gap-6">
                {NavButtons.map((navbutton, index) => (
                  <List className="w-full" key={index}>
                    <Button
                      onClick={() => navigate(navbutton.url)}
                      type="button"
                      className={`${
                        navbutton.name === "Signup"
                          ? "border-2 border-gray-950 before:top-0"
                          : "before:bottom-0 border-b-2 border-transparent hover:border-gray-950"
                      } py-2 px-8 relative z-10 before:content-[''] before:absolute before:left-0 before:w-full before:h-0 before:bg-color2 before:-z-10 hover:before:h-full before:transition-all before:duration-300 before:ease-in text-base`}
                    >
                      {navbutton.name}
                    </Button>
                  </List>
                ))}
              </ul>
            )}
          </div>
          <div className="lg:hidden flex gap-4 items-center">
            <Button className="menu-toggle" onClick={toggleMenu}>
              Menu
            </Button>
          </div>
        </nav>
      </Slide>
      {/* Mobile Nav */}
      <nav
        className={`flex justify-end lg:hidden h-screen w-full bg-gray-950/90 fixed top-0 ${
          open ? "right-0" : "-right-[120vw]"
        } transition-all duration-500 ease-out`}
      >
        <div
          className={`w-[70%] h-screen bg-white flex flex-col justify-between items-center relative ${
            open ? "right-0" : "-right-[120vw]"
          } transition-all duration-500 ease-out delay-300`}
        >
          <section className="w-full px-4 py-6 flex flex-col gap-16">
            <div className="w-full flex justify-between items-center">
              <Image
                as="a"
                href="/"
                className="md:h-10 h-8"
                image={Logo}
                alt="Logo"
              />
              <div
                className="hamburger text-gray-950 cursor-pointer"
                onClick={toggleMenu}
              >
                <ArrowCircleRight
                  size={25}
                  color="currentColor"
                  weight="fill"
                />
              </div>
            </div>
            <ul className="flex flex-col gap-3 pl-2">
              {NavLinks.map((navlink, index) => (
                <List key={index} className="w-full text-base">
                  <NavLink
                    to={navlink.url}
                    onClick={toggleMenu}
                    className="mobile-nav-link-style"
                  >
                    {navlink.name}
                  </NavLink>
                </List>
              ))}
            </ul>
          </section>
        </div>
      </nav>
    </header>
  );
};

export default NavBar;
