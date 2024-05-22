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
        color5: "#2B3A3E",
        purple: {
            300: '#D1C4E9',
            500: '#7B1FA2',
          },
        blue: {
            300: '#64B5F6',
            500: '#2196F3',
          },
        green: {
            300: '#A5D6A7',
            500: '#4CAF50',
          },
        yellow: {
            300: '#FFF59D',
            500: '#FFEB3B',
          },
        orange: {
            300: '#FFCC80',
            500: '#FF9800',
          },
        
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
