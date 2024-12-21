import { Button, Card, CardFooter, CardImg, CardImgOverlay, CardTitle, Col, Container, Navbar, NavbarText, Row } from 'reactstrap';
import StartImage from './images/Start.jpg'
import SaveTheBees from './images/SaveTheBees.jpg'
import NightClubs from './images/NightClub.jpg'
import ChatRoom from './images/ChatRoom.jpg'
import PersonalWebsite from './images/PersonalWebsite.jpg'
import Professional from './images/Professional.jpg'
import { useRef } from 'react';

const centeredText="col-5 mx-auto text-center";
const skills=['Java','Python','C','C++','HTML','CSS','JS','React','Angular','MS Windows','Linux','Unix','Agile','SCRUM','Spring','Flask','NODE.js','WireShark','OOD','JavaFX']

function Header({scrollToSection}){

  return(
    <>
      <Navbar color='dark' className='d-flex'>
        <div>
          <Button color='light' className='navSpacing' onClick={()=>scrollToSection(0)}>Projects</Button>
          <Button color='light' className='navSpacing' onClick={()=>scrollToSection(1)}>About Me</Button>
          <Button color='light' className='navSpacing' onClick={()=>scrollToSection(2)}>Skills</Button>
          <Button color='light' className='navSpacing' onClick={()=>scrollToSection(3)}>The Library</Button>
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
          <p className={centeredText} ref={(el)=>sectionRef.current[0]=el}>Photo: Vayfor</p>
          <h1 className={centeredText}>Recent Projects</h1>
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

function AboutMe({sectionRef}){
  return(
      <div className='d-grid justify-content-center'>
          <h1 className={centeredText} ref={(el)=>sectionRef.current[1]=el}>About Me</h1>
          <p className={centeredText}><strong>I'm currently attending Rochester Institue of Technology persuing
          a BS in Software Engineering with a minor in accounting. I'm Part of the varsity Track team and currently hold a school record in the 500m Dash.</strong><br/></p>
          <img src={Professional} alt='Professional' className='mx-auto professionalStyling'/>
          <br/><p className={centeredText}>I spend a good part of my free time reading books relating to finance/investing, software, philosophy, and phycology (check out the library when it's finished).
          If I'm not reading then I'm either working on a personal project, doing reaserch on companies to invest in, or working out <em>(I need a girlfriend).</em></p>
          <Button as="a" href='https://drive.google.com/file/d/1MllOycx4jSRmRuv3i8FJHC4pTABtuR9P/view?usp=drive_link' target="_Blank" className='mx-auto'>Download Resume</Button>
      </div>
  );
}

function Skills({sectionRef}){
  let container=[];
  for(let i=0;i<skills.length;i++){
    if(i%5===0){
      container.push([<Col className='m-3 skills'>{skills[i]}</Col>]);
      continue;
    }
    else{
      container[container.length-1].push(<Col className='m-3 skills'>{skills[i]}</Col>)
    }
  } 
  return(
      <div>
          <h1 className={centeredText} ref={(el)=>sectionRef.current[2]=el}>Skills</h1>
          <Container className="text-center mw-80">
            {container.map((setOfSkills)=>(
              <Row >
                {setOfSkills}
              </Row>
            ))}
          </Container>
      </div>
  );
}

function TheLibrary({sectionRef}){
  return(
      <div className={centeredText}>
          <h1 ref={(el)=>sectionRef.current[3]=el}>The Library</h1>
          <p>Under Construction...</p>
      </div>
  );
}

function Footer({scrollToSection}){
  return(
    <Container>
        <Row className="text-center">
            <Col>@2024 Conner DeFeo</Col>
            <Col><a href='https://linkedin.com/in/conner-jack-defeo' target='_Blank' rel='noreferrer' className='text-light'>Linkedin</a></Col>
            <Col><Button onClick={()=>window.scroll({top:0, behavior:'smooth'})} className='bg-light text-dark'>BACK TO TOP</Button></Col>
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
      <Header scrollToSection={scrollToSection} sectionRef={sectionRef}/>
      <Projects sectionRef={sectionRef}/>
      <AboutMe sectionRef={sectionRef}/>
      <Skills sectionRef={sectionRef}/>
      <TheLibrary sectionRef={sectionRef}/>
      <Footer scrollToSection={scrollToSection}/>
    </div>
  );
}

export default App;
