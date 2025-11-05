import type {userProp} from "../../Types/User.ts";

export default function User(prop:userProp){
    return(
        <tr className="border-b-2 bg-blue-100">
            <td className="px-2 py-1 ">{prop.userInfo.userId}</td>
            <td className="px-2 py-1 ">{prop.userInfo.username}</td>
            <td className="px-2 py-1 ">{prop.userInfo.role}</td>
            <td><button className={"rounded-2xl pr-2 pl-2 bg-blue-800 " +
                "text-white transition delay-50 ease-in-out hover:bg-blue-500"}>Update</button></td>
            <td ><button className={"pr-2 pl-2 rounded-2xl w-20 bg-red-800 " +
                "text-white transition delay-50 ease-in-out hover:bg-red-500"}>Delete</button></td>
        </tr>
    );
}