import React, { useContext } from 'react';
import BookForm from './BookForm';
import BooksContext from '../context/BooksContext';
import {useNavigate} from 'react-router-dom';

const AddBook = ({ history }) => {
  const navigate = useNavigate();

  const { books, setBooks } = useContext(BooksContext);

  const handleOnSubmit = (book) => {
    setBooks([book, ...books]);
    navigate('/');
  };

  return (
    <React.Fragment>
      <BookForm handleOnSubmit={handleOnSubmit} />
    </React.Fragment>
  );
};

export default AddBook;
