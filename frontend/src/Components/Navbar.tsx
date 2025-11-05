import {Outlet, Link} from "react-router-dom";
import {userStore} from "../Stores/UserStore.ts";

export default function Navbar(){

    const currentUser = userStore.getState().user;

    return(
        <div className={"pt-0.5"}>
        <div className={"NavBarContainer bg-[#8f571b] pb-0.5 flex justify-between rounded-2xl"}>
        <h1 className={"text-3xl text-white ml-3 "}>Library</h1>
            {currentUser.role == "ADMIN" ? <div>
                <Link to={"/dashboard/admin"}
                      className="text-2xl text-white px-3 py-0.5 rounded-2xl
                transition delay-50 ease-in-out
                hover:bg-yellow-900 hover:text-blue-400">Users</Link>
            </div> : <></>}
            <Link
                className="text-2xl text-white ml-20 px-3 py-1 rounded-2xl
                transition delay-50 ease-in-out
                hover:bg-yellow-900 hover:text-blue-400"
                to="/dashboard/home"
                >
                Home
        </Link>
            <div>
                <Link to={"/dashboard/myBooks"}
                      className="text-2xl text-white px-3 py-0.5 rounded-2xl
                transition delay-50 ease-in-out
                hover:bg-yellow-900 hover:text-blue-400">To My Books</Link>
            </div>
            <div className={"flex flex-row gap-4"}>
                <Link  className="text-2xl text-white px-3 py-0.5 rounded-2xl
                transition delay-50 ease-in-out
                hover:bg-yellow-900 hover:text-blue-400" to={"/dashboard/books"}>To the available books</Link>
            </div>
        </div>
        <Outlet/>
    </div>);
}