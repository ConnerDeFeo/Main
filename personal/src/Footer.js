import { Col, Container, Row, Button } from "reactstrap";

function Footer(){
    return(
      <Container className="bg-dark text-light">
          <Row className="text-center">
              <Col>@2024 Conner DeFeo</Col>
              <Col><a href='https://linkedin.com/in/conner-jack-defeo' target='_Blank' rel='noreferrer' className='text-light text-decoration-none'>Linkedin</a></Col>
              <Col><Button onClick={()=>window.scroll({top:0, behavior:'smooth'})} className='bg-light text-dark'>BACK TO TOP</Button></Col>
          </Row>
      </Container>
    );
}
  

export default Footer;