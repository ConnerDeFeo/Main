import { Col, Container, Row } from "reactstrap";
import NO_IMAGE from'./images/NoImage.jpg'

function PersonalWebsite(){
    return(
        <>
            <Container>
                <Row>
                    <Col>
                        <em>Personal</em> - React, Reactstrap, Bootstrap
                    </Col>
                    <Col className="text-end">
                        2025
                    </Col>
                </Row>
                <Row>
                    <img src={NO_IMAGE} alt="NoImg" className="mx-auto"/>
                </Row>
                <Row>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, 
                quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum 
                dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                </Row>
            </Container>
        </>
    );
}

export default PersonalWebsite;