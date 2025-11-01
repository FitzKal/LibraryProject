import {Outlet, Link} from "react-router-dom";

export default function Navbar(){


    return(
        <div className={"pt-0.5"}>
        <div className={"NavBarContainer bg-[#8f571b] pb-0.5 flex justify-between rounded-2xl"}>
        <h1 className={"text-3xl text-white ml-3 "}>Library</h1>
            <Link
                className="text-2xl text-white ml-20 px-3 py-1 rounded-2xl
                transition delay-50 ease-in-out
                hover:bg-yellow-900 hover:text-blue-400"
                to="/dashboard/home"
                >
                Home
        </Link>
            <div className={"flex flex-row gap-4"}>
                <Link  className={"text-blue-300 pr-2 pl-2 mr-3 mt-1 transition delay-50 ease-in-out rounded-2xl" +
                    " hover:bg-yellow-900 hover:text-blue-400"} to={"/dashboard/books"}>To the available books</Link>
            </div>
        </div>
        <Outlet/>
    </div>);
}