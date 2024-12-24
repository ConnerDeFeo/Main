import { BrowserRouter, Routes, Route } from 'react-router-dom'; 
import Home from './Home';
import Contact from './Contact';
import PersonalWebsite from './PersonalWebsite';

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/contact" element={<Contact/>} />
        <Route path="/personalWebsite" element={<PersonalWebsite/>} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;