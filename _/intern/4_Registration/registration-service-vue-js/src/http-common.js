import axios from "axios";

const instance = axios.create({
  baseURL: "http://195.38.164.139:5556/api",
  // baseURL: "http://localhost:5556/api",

  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;