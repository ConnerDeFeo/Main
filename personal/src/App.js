import { Button, Card, CardFooter, CardImg, CardImgOverlay, CardTitle, Col, Container, Navbar, NavbarText, Row } from 'reactstrap';
import StartImage from './images/Start.jpg'
// import { useRef } from 'react';

function Header(){
  return(
    <>
      <Navbar color='dark' className='d-flex'>
        <div>
          <Button color='light' className='navSpacing'>Projects</Button>
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

function Projects(){
  return(
      <>
          <p className="d-flex justify-content-center">Photo: Vayfor</p>
          <h1 className="d-flex justify-content-center">Recent Projects</h1>
          <Container>
              <Row>
                  <Col>
                      <Card>
                          <CardImg></CardImg>
                          <CardFooter>This Website! - <em>Personal</em>, 2024</CardFooter>
                      </Card>
                  </Col>
                  <Col>
                      <Card>
                          <CardImg></CardImg>
                          <CardFooter>Save The Bees - <em>Intro to Software Engineering</em>, 2024</CardFooter>
                      </Card>
                  </Col>
              </Row>
              <Row>
                  <Col>
                      <Card>
                          <CardImg></CardImg>
                          <CardFooter>Full-Stack Nightclubs - <em>Web Engineering</em>, 2024</CardFooter>
                      </Card>
                  </Col>
                  <Col>
                      <Card>
                          <CardImg></CardImg>
                          <CardFooter>PostgreSQL Databases - <em>Web Engineering</em>, 2024</CardFooter>
                      </Card>
                  </Col>
              </Row>
          </Container>        
      </>
  );
}

function AboutMe(){
  return(
      <div className="d-grid justify-content-center">
          <h1 className="d-flex justify-content-center">About Me</h1>
          <p className="text-center">Stuff About Me</p>
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
  // const sectionRef = useRef(null);

  // // Function to scroll to the section
  // const scrollToSection = () => {
  //   sectionRef.current.scrollIntoView({ behavior: 'smooth' });
  // };
  return (
    <div className='bg-dark text-light'>
      <Header/>
      <Projects/>
      <AboutMe/>
      <Skills/>
      <Experience/>
      <TheLibrary/>
      <Footer/>
    </div>
  );
}

export default App;
