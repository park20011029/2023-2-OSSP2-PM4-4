/** @type {import('tailwindcss').Config} */
const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {},
  },
  plugins: [],
  resolve: {
    fallback: {
      net: false,
    },
  },
}



