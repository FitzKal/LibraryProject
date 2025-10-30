import "./index.css";
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import {createBrowserRouter, RouterProvider} from "react-router-dom"
import {QueryClientProvider,QueryClient} from "@tanstack/react-query";

const router = createBrowserRouter([
    {
        path:"/",
        element: <p className={"text-red-400"}>hello</p>,
        errorElement: <div>404 not found</div>
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
