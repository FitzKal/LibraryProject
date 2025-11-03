import type {bookElementProp} from "../../Types/Book.ts";
import Book from "./Book.tsx";

export default function BookElement({onDelete,bookInfo}:bookElementProp){
    return(<div>
        <Book bookInfo={bookInfo}/>
        <button className={"text-l border-2 rounded-2xl pl-1 pr-1 w-20 bg-red-800 " +
            "text-white transition delay-50 ease-in-out hover:bg-red-500"}
                onClick={()=>onDelete(bookInfo.id)}>Delete</button>
    </div>);
}