import { BrowserRouter, Routes, Route, useLocation, useNavigate } from 'react-router-dom'; 
import Home from './Home';
import Contact from './Contact';
import PersonalWebsite from './PersonalWebsite';
import { Button, Col, Container, Navbar, NavbarText, Row } from 'reactstrap';

function Navigation(){
  const location = useLocation();
  const navigate = useNavigate();

  const handleNavigation = (path) => {
    if (location.pathname !== path) {
      navigate(path);
    }
  };

  return (
    <Navbar className='d-flex bg-black'>
      <Button color='light' className="navButton" onClick={() => handleNavigation('/')}>Home</Button>
      <NavbarText className="text-light">
        <p>Rochester, NY | Full-Stack Developer</p>
      </NavbarText>
      <Button color="light" className="navButton" onClick={() => handleNavigation('/contact')}>Contact</Button>
    </Navbar>
  );
};


const App = () => {
  return (
    <BrowserRouter>
      <Navigation/>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/contact" element={<Contact/>} />
        <Route path="/personalWebsite" element={<PersonalWebsite/>} />
      </Routes>
      <Container className="bg-dark text-light mw-100 pTop">
          <Row className="text-center">
              <Col><p>@2024 Conner DeFeo</p></Col>
              <Col><a href='https://linkedin.com/in/conner-jack-defeo' target='_Blank' rel='noreferrer' className='text-light text-decoration-none'><p className="linkedin d-inline-block">Linkedin</p></a></Col>
              <Col><p onClick={()=>window.scroll({top:0, behavior:'smooth'})} className='backToTop d-inline-block'>BACK TO TOP</p></Col>
          </Row>
      </Container>
    </BrowserRouter>
  );
};

export default App;