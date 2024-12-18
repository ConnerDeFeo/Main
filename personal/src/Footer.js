import { Col, Container, Row } from "reactstrap";

function Footer(){
    return(
        <div className="bg-dark text-light">
            <h1>Footer</h1>
            <Container>
                <Row className="text-center">
                    <Col>1</Col>
                    <Col>2</Col>
                    <Col>3</Col>
                    <Col>4</Col>
                </Row>
            </Container>
        </div>
    );
}

export default Footer;