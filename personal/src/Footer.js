import { Col, Container, Row} from "reactstrap";

function Footer(){
    return(
      <Container className="bg-dark text-light mw-100 pTop">
          <Row className="text-center">
              <Col><p>@2024 Conner DeFeo</p></Col>
              <Col><a href='https://linkedin.com/in/conner-jack-defeo' target='_Blank' rel='noreferrer' className='text-light text-decoration-none'><p className="linkedin d-inline-block">Linkedin</p></a></Col>
              <Col><p onClick={()=>window.scroll({top:0, behavior:'smooth'})} className='backToTop d-inline-block'>BACK TO TOP</p></Col>
          </Row>
      </Container>
    );
}
  

export default Footer;