import "./index.css";
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import {createBrowserRouter, RouterProvider,Navigate} from "react-router-dom"
import {QueryClientProvider,QueryClient} from "@tanstack/react-query";
import LoginForm from "./Components/Auth/LoginForm.tsx";
import Navbar from "./Components/Navbar.tsx";
import RegisterForm from "./Components/Auth/RegisterForm.tsx";
import HomePage from "./Components/HomePage.tsx";
import DisplayBooks from "./Components/Book/DisplayBooks.tsx";
import BookPage from "./Components/Book/BookPage.tsx";

const router = createBrowserRouter([
    {
        path:"/",
        element: <Navigate to={"/login"}/>,
        errorElement: <div>404 not found</div>
    },
    {
        path:"/login",
        element: <LoginForm />
    },
    {
        path:"/dashboard",
        element:<Navbar/>,
        children:[
            {
                path:"/dashboard/home",
                element:<HomePage />
            },
            {
                path:"/dashboard/books",
                element:<DisplayBooks />,
            },
            {
                path:"/dashboard/books/:param",
                element:<BookPage />
            },
        ]
    },
    {
        path: "/register",
        element:<RegisterForm />
    }
])
    const query = new QueryClient();
createRoot(document.getElementById('root')!).render(
  <StrictMode>
     <QueryClientProvider client={query}>
        <RouterProvider router={router}/>
        <App />
     </QueryClientProvider>
  </StrictMode>,
)
