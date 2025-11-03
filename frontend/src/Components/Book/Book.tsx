import type {bookProp} from "../../Types/Book.ts";
import {Link} from "react-router-dom";


export default function Book(props:bookProp){

    return(
        <div className={"flex flex-col w-60"}>
            <Link to={`/dashboard/books/${props.bookInfo.id}`} className={"text-2xl mb-3 transition delay-50 ease-in-out hover:text-blue-700"}>{props.bookInfo.title}</Link>
            <img className={"h-80 w-55 mb-2 transition delay-150 duration-300 ease-in-out hover:scale-130"} src={props.bookInfo.pictureSRC} alt={props.bookInfo.title}/>
            <p>Author: {props.bookInfo.author}</p>
            <p>Genre: {props.bookInfo.genre}</p>
            <p className={"mb-2"}>Added by: {props.bookInfo.username}</p>
        </div>
    )
}