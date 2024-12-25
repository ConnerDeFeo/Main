import { Button, Card, CardFooter, CardImg, CardImgOverlay, CardTitle, Col, Container, Row } from 'reactstrap';
import StartImage from './images/Start.jpg'
import SaveTheBees from './images/SaveTheBees.jpg'
import NightClubs from './images/NightClub.jpg'
import ChatRoom from './images/ChatRoom.jpg'
import PersonalWebsite from './images/PersonalWebsite.jpg'
import Professional from './images/Professional.jpg'
import Footer from './Footer';
import NavigationBar from './NavigationBar';
import { useNavigate } from 'react-router-dom';

const centeredText="col-5 mx-auto text-center";
const skills=['Java','Python','C','C++','HTML','CSS','JS','React','Angular','MS Windows','Linux','Unix','Agile','SCRUM','Spring','Flask','NODE.js','WireShark','OOD','JavaFX']

function Header(){
  /*Basic navbar. Div tags are used here to seperate the left and right sections
  using a flex container. navSpacing is a custom css class that keeps that keeps spacing consistant.*/
  return(
    <>
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
  const navigate=useNavigate();
  function project(image, alt, text, date){
    return(
      <Col>
        <Card className='m-3 projectCardCustom' onClick={()=>navigate(alt)}>
          <CardImg src={image} alt={alt} style={{height:'65vh',objectFit:'cover'}}/>
          <CardFooter><Container><Row><Col style={{position:'absolute'}}><strong>{text}</strong></Col><Col className='text-end'><strong>{date}</strong></Col></Row></Container></CardFooter>
        </Card>
      </Col>
    );
  }
  return(
      <>
          <p className={centeredText}>Photo: Vayfor</p>
          <h1 className={centeredText}>Recent Projects</h1>
          <Container className='mw-100'>
              <Row>
                {project(PersonalWebsite,"/personalWebsite",<>This Website! (React) - <em>Personal</em></>,<>2024</>)}
                {project(SaveTheBees,"Save The Bees",<>Save The Bees (Full-Stack) - <em>Intro to Software Engineering</em></>,<>2024</>)}
              </Row>
              <Row>
                {project(NightClubs,"Night Clubs",<>Nightclubs (Full-Stack)- <em>Web Engineering</em></>,<>2024</>)}
                {project(ChatRoom,"Chat Room",<>ChatRoom (PostgreSQL Databases) - <em>Web Engineering</em></>,<>2024</>)}
              </Row>
          </Container>        
      </>
  );
}

function AboutMe(){
  return(
      <div className='d-grid justify-content-center'>
          <h1 className={centeredText}>About Me</h1>
          <p className={centeredText}><strong>I'm currently attending Rochester Institue of Technology persuing
          a BS in Software Engineering with a minor in accounting. I'm Part of the varsity Track team and currently hold a school record in the 500m Dash.</strong><br/></p>
          <img src={Professional} alt='Professional' className='mx-auto professionalStyling'/>
          <br/><p className={centeredText}>I spend a good part of my free time reading books relating to finance/investing, software, philosophy, and phycology 
          (check out "The Library" when it's finished). If I'm not reading then I'm either working on a personal project, doing reaserch on companies to invest in, 
          or working out <em>(I need a girlfriend).</em></p>
          <Button as="a" href='https://drive.google.com/file/d/1MllOycx4jSRmRuv3i8FJHC4pTABtuR9P/view?usp=drive_link' target="_Blank" color='light' className='mx-auto navButton'>Download Resume</Button>
      </div>
  );
}

function Skills(){
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
          <h1 className={centeredText}>Skills</h1>
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
    <div className='bg-dark text-light'>
      <NavigationBar />
      <Header/>
      <Projects />
      <AboutMe />
      <Skills />
      <Footer/>
    </div>
  );
}

export default Home;
