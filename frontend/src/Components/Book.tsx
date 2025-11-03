import type {bookProp} from "../Types/Book.ts";

export default function Book(props:bookProp){
    return(
        <div>
            <h1>{props.bookInfo.title}</h1>
            <img src={props.bookInfo.pictureSRC} alt={props.bookInfo.title}/>
            <p>{props.bookInfo.description}</p>
            <p>{props.bookInfo.genre}</p>
            <p>Added by: {props.bookInfo.username}</p>
        </div>
    )
}