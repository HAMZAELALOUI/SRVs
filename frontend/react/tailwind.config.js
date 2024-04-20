/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        color1: "#D7573B",
        color2: "#FA9C0F",
        color3: "#152F37",
        color4: "#35528B",
      },
      margin: {
        sm: '8px',
        md: '16px',
        lg: '24px',
        xl: '48px',
      },
      fontFamily: {
        poppins: ["Poppins"],
      },
    },
  },
  plugins: [],
};
