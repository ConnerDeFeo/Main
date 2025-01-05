import { Button, Card, CardFooter, CardImg, Col, Container, Row } from 'reactstrap';
import StartImage from './images/Start.jpg'
import SaveTheBees from './images/SaveTheBees.jpg'
import NightClubs from './images/NightClub.jpg'
import ChatRoom from './images/ChatRoom.jpg'
import PersonalWebsite from './images/PersonalWebsite.jpg'
import Professional from './images/Professional.jpg'
import { useNavigate } from 'react-router-dom';
import agile from './images/skills/agile.webp';
import angular from './images/skills/angular.webp';
import c from './images/skills/C.webp';
import cPlusPlus from './images/skills/C++.webp';
import css from './images/skills/css.webp';
import flask from './images/skills/flask.webp';
import html from './images/skills/html.webp';
import java from './images/skills/Java.webp';
import javaFx from './images/skills/JavaFX.webp';
import js from './images/skills/js.webp';
import linux from './images/skills/linux.webp';
import nodejs from './images/skills/nodeJS.webp';
import oop from './images/skills/OOP.png';
import python from './images/skills/python.webp';
import react from './images/skills/React.webp';
import scrum from './images/skills/scrum.webp';
import spring from './images/skills/spring.png';
import windows from './images/skills/Windows.webp';
import wireshark from './images/skills/wireshark.webp';

const skillImages = {'agile':agile,'angular':angular,'c':c,'c++':cPlusPlus,'css':css,'flask':flask,'html':html,'java':java,'javaFx':javaFx,
'js':js,'linux':linux,'nodejs':nodejs,'oop':oop,'python':python,'react':react,'scrum':scrum,'spring':spring,'windows':windows,'wireshark':wireshark};

const centeredText="col-10 col-lg-6 mx-auto text-center";

function Header(){
  /*Basic navbar. Div tags are used here to seperate the left and right sections
  using a flex container. navSpacing is a custom css class that keeps that keeps spacing consistant.*/
  return(
    <>
      <img src={StartImage} alt='Start Image 400m' style={{width:'100%'}}/>
      <p className={centeredText}>Photo: Vayfor</p>
    </>
  );
}

function Projects(){
  const navigate=useNavigate();
  function project(image, alt, text, date){
    return(
      <Col className='col-12 col-md-6'>
        <Card className='m-3 projectCardCustom' onClick={()=>navigate(alt)}>
          <CardImg src={image} alt={alt} className='cardImageCustom'/>
          <CardFooter><Container><Row><Col style={{position:'absolute'}}><strong>{text}</strong></Col><Col className='text-end'><strong>{date}</strong></Col></Row></Container></CardFooter>
        </Card>
      </Col>
    );
  }
  return(
      <>
          <h1 className={centeredText}>Recent Projects</h1>
          <Container className='mw-100'>
              <Row>
                {project(PersonalWebsite,"personalWebsite",<>This Website! - <em>Personal</em></>,<>2025</>)}
                {project(SaveTheBees,"saveTheBees",<>Save The Bees - <em>Intro to SWEN</em></>,<>2024</>)}
              </Row>
              <Row>
                {project(NightClubs,"nightClubs",<>Nightclubs - <em>Web Engineering</em></>,<>2024</>)}
                {project(ChatRoom,"chatRoom",<>ChatRoom - <em>Web Engineering</em></>,<>2024</>)}
              </Row>
          </Container>        
      </>
  );
}

function AboutMe(){
  return(
      <div className='d-grid justify-content-center'>
          <h1 className={centeredText+" my-4"}>About Me</h1>
          <p className={centeredText}><strong>I am currently attending the Rochester Institute of Technology, pursuing a BS in 
            Software Engineering with a minor in Accounting. I am part of the varsity track team and currently hold the school record in the 500m dash.</strong><br/></p>
          <img src={Professional} alt='Professional' className='mx-auto professionalStyling'/>
          <br/><p className={centeredText}>I spend a good part of my free time reading books relating to finance/investing, software, philosophy, and phycology 
          (check out "The Library" when it's finished). If I'm not reading then I'm either working on a personal project, doing reaserch on companies to invest in, 
          or working out <em>(I need a girlfriend).</em></p>
          <Button as="a" href='https://drive.google.com/file/d/1MllOycx4jSRmRuv3i8FJHC4pTABtuR9P/view?usp=drive_link' 
          target="_Blank" color='light' className='mx-auto resumeDownload my-5 border border-black'>Download Resume</Button>
      </div>
  );
}

function Skills(){
  const keys=Object.keys(skillImages);

  return(
      <div>
          <h1 className={centeredText+" my-4"}>Skills</h1>
          <Container className="text-center mw-80">
              <Row >
                {keys.map((key,index)=>(<Col xs={6} sm={6} md={2}><img src={skillImages[key]} alt='key' key={index} className='skillImages my-3'/></Col>))}
              </Row>
          </Container>
      </div>
  );
}

// function TheLibrary(){
//   return(
//       <div className={centeredText}>
//           <h1>The Library</h1>
//           <p>Under Construction...</p>
//       </div>
//   );
// }

function Home() {  
  return (
    <div>
      <Header/>
      <Projects />
      <AboutMe />
      <Skills />
    </div>
  );
}

export default Home;
