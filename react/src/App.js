import React from 'react';
import './App.css';
import RoutineApp from './routineApp'
import GoalList from './goalList'
import 'bootstrap/dist/css/bootstrap.min.css';


class App extends React.Component {
  constructor(props) {
    super(props)
    this.state = {selection:"home", goals:[]}
    this.addRoutineToGoals = this.addRoutineToGoals.bind(this);
    this.completeGoal = this.completeGoal.bind(this);
  }

  select(selection) {
    let that = this
    return function() {
      that.setState({selection:selection})
    }
  }

  completeGoal(goal) {
    let that = this;
    return function(e) {
      that.setState((state) => {
        let index = that.state.goals.indexOf(goal)
        state.goals.splice(index, 1)
        return {goals: state.goals}
      })
    }
  }

  addRoutineToGoals(goals) {
    let that = this
    return function() {
      that.setState((state, props) => {
        state.goals.push.apply(state.goals, goals)
        return state
      })
      console.log(that.state)
    }
  }

  render() {
    switch(this.state.selection) {
      case "routines":
        return (
          <div className='container'>
            <RoutineApp
              toHome={this.select("home")}
              addRoutineToTodo={this.addRoutineToGoals}/>
          </div>
          )
      case "goals":
        return (
          <div className='container'>
            <GoalList
              goals={this.state.goals}
              toHome={this.select("home")}
              completeGoal={this.completeGoal}/>
          </div>
        )
      default:
        return (
          <div className='container'>
            <a onClick={this.select("goals")} href="#goals">Goals</a>
            <br/>
            <a onClick={this.select("routines")} href="#routines">Routines</a>
          </div>
        )

    }
  }
}

export default App;
