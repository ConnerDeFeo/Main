import { BrowserRouter, Routes, Route } from 'react-router-dom'; 
import Home from './Home';
import Contact from './Contact';

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/contact" element={<Contact />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;