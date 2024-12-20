import { Button, Card, CardFooter, CardImg, CardImgOverlay, CardTitle, Col, Container, Navbar, NavbarText, Row } from 'reactstrap';
import StartImage from './images/Start.jpg'
import SaveTheBees from './images/SaveTheBees.jpg'
import NightClubs from './images/NightClub.jpg'
import ChatRoom from './images/ChatRoom.jpg'
import PersonalWebsite from './images/PersonalWebsite.jpg'
import { useRef } from 'react';

function Header({scrollToSection}){

  return(
    <>
      <Navbar color='dark' className='d-flex'>
        <div>
          <Button color='light' className='navSpacing' onClick={()=>scrollToSection(0)}>Projects</Button>
          <Button color='light' className='navSpacing'>About Me</Button>
          <Button color='light' className='navSpacing'>Skills</Button>
          <Button color='light' className='navSpacing'>Experience</Button>
          <Button color='light' className='navSpacing'>The Library</Button>
        </div>
        <div>
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
    </>
  );
}

function project(image, alt, text){
  return(
    <Col>
      <Card className='m-3'>
        <CardImg src={image} alt={alt} style={{height:'48vh',objectFit:'cover'}}/>
        <CardFooter>{text}</CardFooter>
      </Card>
    </Col>
  );
}

function Projects({sectionRef}){
  return(
      <>
          <p className="d-flex justify-content-center" ref={(el)=>sectionRef.current[0]=el}>Photo: Vayfor</p>
          <h1 className="d-flex justify-content-center">Recent Projects</h1>
          <Container className='mw-100'>
              <Row>
                {project(PersonalWebsite,"Personal Website",<>This Website! (React) - <em>Personal</em>, 2024</>)}
                {project(SaveTheBees,"Save The Bees",<>Save The Bees (Full-Stack) - <em>Intro to Software Engineering</em>, 2024</>)}
              </Row>
              <Row>
                {project(NightClubs,"Night Clubs",<>Nightclubs (Full-Stack)- <em>Web Engineering</em>, 2024</>)}
                {project(ChatRoom,"Chat Room",<>ChatRoom (PostgreSQL Databases) - <em>Web Engineering</em>, 2024</>)}
              </Row>
          </Container>        
      </>
  );
}

function AboutMe(){
  return(
      <div className="d-grid justify-content-center">
          <h1 className="d-flex justify-content-center">About Me</h1>
          <p className="text-center"></p>
          <Button>Download Resume</Button>
      </div>
  );
}

function Skills(){
  return(
      <div className="d-grid justify-content-center">
          <h1 className="text-center">Skills</h1>
          <p className="text-center">Some grid potentially showing skills</p>
      </div>
  );
}

function Experience(){
  return(
    <div className="d-grid justify-content-center">
      <h1 className="text-center">Experience</h1>
      <p className="text-center">Grid with experience</p>
    </div>
  );
}

function TheLibrary(){
  return(
      <div className="d-grid justify-content-center">
          <h1>The Library</h1>
          <p>I have no clue how to do this yet</p>
      </div>
  );
}

function Footer(){
  return(
    <Container>
        <Row className="text-center">
            <Col>@2024 Conner DeFeo</Col>
            <Col>Linkedin</Col>
            <Col>BACK TO TOP</Col>
        </Row>
    </Container>
  );
}

function App() {
  const sectionRef=useRef([]);

  const scrollToSection = (index) => {
    sectionRef.current[index].scrollIntoView({ behavior: "smooth" });
  };  
  return (
    <div className='bg-dark text-light'>
      <Header scrollToSection={scrollToSection}/>
      <Projects sectionRef={sectionRef}/>
      <AboutMe/>
      <Skills/>
      <Experience/>
      <TheLibrary/>
      <Footer/>
    </div>
  );
}

export default App;
