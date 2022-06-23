import React from 'react';
import { BrowserRouter, Route, Routes, Router, Navigate } from 'react-router-dom';
import Header from '../components/Header';
import AddBook from '../components/AddBook';
import BooksList from '../components/BooksList';
import useRestApi from '../hooks/useRestApi';
import EditBook from '../components/EditBook';
import BooksContext from '../context/BooksContext';

const AppRouter = () => {
  const [books, setBooks] = useRestApi('books', []);

  return (
    <BrowserRouter>
      <div>
        <Header />
        <div className="main-content">
          <BooksContext.Provider value={{ books, setBooks }}>

            <Routes>
              <Route element={<BooksList/>} path="/" exact={true} />
              <Route element={<AddBook/>} path="/add" />
              <Route element={<EditBook/>} path="/edit/:id" />
              <Route element={() => <Navigate  to="/" />} />
            </Routes>

          </BooksContext.Provider>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default AppRouter;
