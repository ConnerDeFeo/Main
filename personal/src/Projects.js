import { Card, CardFooter, CardImg, CardTitle, Col, Container, Row } from "reactstrap";

function Projects(){
    return(
        <div className="bg-dark text-light">
            <p className="d-flex justify-content-center">Photo: Vayfor</p>
            <h1 className="d-flex justify-content-center">Recent Projects</h1>
            <Container>
                <Row>
                    <Col>
                        <Card>
                            <CardTitle>Project 1</CardTitle>
                            <CardImg></CardImg>
                            <CardFooter>Some date</CardFooter>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <CardTitle>Project 2</CardTitle>
                            <CardImg></CardImg>
                            <CardFooter>Some date</CardFooter>
                        </Card>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Card>
                            <CardTitle>Project 3</CardTitle>
                            <CardImg></CardImg>
                            <CardFooter>Some date</CardFooter>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <CardTitle>Project 4</CardTitle>
                            <CardImg></CardImg>
                            <CardFooter>Some date</CardFooter>
                        </Card>
                    </Col>
                </Row>
            </Container>        
        </div>
    );
}

export default Projects;