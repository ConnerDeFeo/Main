import { Col, Container, Row } from "reactstrap";

function ProjectPage({type,languages,date,video,description}){
    return(
        <>
            <Container className="fs-4">
                <Row className="mt-4">
                    <Col>
                        <em>{type}</em> - {languages}
                    </Col>
                    <Col className="text-end">
                        {date}
                    </Col>
                </Row>
                <Row className="my-4">
                    <img src={video} alt="NoImg" className="mx-auto"/>
                </Row>
                <Row>
                    {description}
                </Row>
            </Container>
        </>
    );
}

export default ProjectPage;