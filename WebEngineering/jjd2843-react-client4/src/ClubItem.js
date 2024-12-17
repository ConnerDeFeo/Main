import React,{ Component} from "react";
import { Button, Row,Col, Container, Label } from 'reactstrap';

class ClubItem extends Component{
    render(){
        let bg;
        if(this.props.counter<this.props.yellowThreshold){
            bg="";
        }
        else if(this.props.counter<this.props.maxCap){
            bg="bg-warning";
        }
        if(this.props.counter===this.props.maxCap){
            bg="bg-danger";
        }
        return(
            <Container className="border border-dark m-2 text-center custom-rounded" >
                <Row className="bg-secondary text-light custom-rounded-inner">
                    <Label className="fs-5">Name</Label>
                    <p className="">{this.props.name}</p>
                </Row>
                <Row className={bg}>
                    <Label className="fs-5">Location</Label>
                    <p>{this.props.location}</p>
                </Row>
                <Row className="bg-secondary text-light">
                    <Label className="fs-5">Music Genre</Label>
                    <p>{this.props.genre}</p>
                </Row>
                <Row className={bg}>
                    <Label className="fs-5">Yellow Threshold</Label>
                    <p>{this.props.yellowThreshold}</p>
                </Row>
                <Row className="bg-secondary text-light">
                    <Label className="fs-5">Max Capacity</Label>
                    <p>{this.props.maxCap}</p>
                </Row>
                <Row>
                    <Label className="fs-5">Current Capacity</Label>
                    <p>{this.props.counter}</p>
                </Row>
                <Row>
                    <Col>
                        <Button className="m-1" size="lg" color="info" onClick={this.props.incrementCounter} disabled={this.props.counter===this.props.maxCap}>+</Button>
                    </Col>
                    <Col>
                        <Button className="m-1" size="lg" color="dark" onClick={this.props.decrementCounter} disabled={this.props.counter===0}>-</Button>
                    </Col>
                    <Col>
                        <Button className="m-1" size="lg" color="warning" onClick={this.props.toggleEdit}>Edit</Button>
                    </Col>
                    <Col>
                        <Button className="m-1" size="lg" color="danger" onClick={this.props.deleteClub}>Delete</Button>
                    </Col>
                </Row>
            </Container> 
        );
    }
}

export default ClubItem;