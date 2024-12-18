import { Button, Card, CardImg, CardImgOverlay, CardTitle, Navbar, NavbarText, NavbarToggler } from 'reactstrap';
import StartImage from './images/Start.jpg'
import Projects from './Projects';
import Skills from './Skills';
import AboutMe from './AboutMe';
import TheLibrary from './TheLibrary';
import Footer from './Footer';

function App() {
  return (
    <div className='bg-dark'>
      <Navbar color='secondary' className='d-flex'>
        <NavbarToggler className='navbarMenu'/>
        <div className='ms-auto'>
          <NavbarText className='text-light navSpacing'>Conner DeFeo | Rochester,NY | Full-Stack Developer</NavbarText>
          <Button color='light'>Contact</Button>
        </div>
      </Navbar>
      <Card className='bg-dark'>
        <CardImg src={StartImage} alt='Start Image 400m'/>
        <CardImgOverlay>
          <CardTitle className='cardText'>Conner DeFeo</CardTitle>
        </CardImgOverlay>
      </Card>
      <Projects/>
      <AboutMe/>
      <Skills/>
      <TheLibrary/>
      <Footer/>
    </div>
  );
}

export default App;
