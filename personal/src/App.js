import { BrowserRouter, Routes, Route, useLocation, useNavigate } from 'react-router-dom'; 
import Home from './Home';
import Contact from './Contact';
import { Button, Col, Container, Navbar, NavbarText, Row } from 'reactstrap';
import ProjectPage from './ProjectPage';
import NO_IMAGE from './images/NoImage.jpg'

const lIpsum=<>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, 
quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum 
dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</>

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
        <Route path="/personalWebsite" element={<ProjectPage title={'PERSONAL WEBSITE'} type={'Personal'} languages={'React, Bootstrap, Reactstrap'} date={'2025'} video={NO_IMAGE} description={lIpsum}/>} />
        <Route path="/saveTheBees" element={<ProjectPage title={'SAVE THE BEES'} type={'Inro to Software Engineering'} languages={'Angular, Java, SpringBoot'} date={'2024'} video={NO_IMAGE} description={lIpsum}/>} />
        <Route path="/nightClubs" element={<ProjectPage title={'NIGHT CLUBS'} type={'Web Engineering'} languages={'React, Bootstrap, Reactstrap, Python, Flask, PostgreSQL'} date={'2024'} video={NO_IMAGE} description={lIpsum}/>} />
        <Route path="/chatRoom" element={<ProjectPage title={'CHAT ROOM'} type={'Web Engineering'} languages={'Python, PostgreSQL'} date={'2024'} video={NO_IMAGE} description={lIpsum}/>} />
      </Routes>
      <Container className="mw-100 pTop">
          <Row className="text-center">
              <Col><p>@2025 Conner DeFeo</p></Col>
              <Col><a href='https://linkedin.com/in/conner-jack-defeo' target='_Blank' rel='noreferrer' className='text-light text-decoration-none'><p className="linkedin d-inline-block">Linkedin</p></a></Col>
              <Col><p onClick={()=>window.scroll({top:0, behavior:'smooth'})} className='backToTop d-inline-block'>BACK TO TOP</p></Col>
          </Row>
      </Container>
    </BrowserRouter>
  );
};

export default App;