use regex::Regex;
use std::io;
use std::io::BufRead;

#[derive(Debug, Clone, Copy)]
struct Planet {
    pos: (isize, isize, isize),
    v: (isize, isize, isize),
}

impl Planet {
    fn potential_energy(&self) -> isize {
        return self.pos.0.abs() + self.pos.1.abs() + self.pos.2.abs();
    }

    fn kinect_energy(&self) -> isize {
        return self.v.0.abs() + self.v.1.abs() + self.v.2.abs();
    }

    fn total_energy(&self) -> isize {
        return self.potential_energy() * self.kinect_energy();
    }
}

fn change_planets_by_gravity(mut p1: Planet, mut p2: Planet) -> (Planet, Planet) {
    if p2.pos.0 > p1.pos.0 {
        p2.v.0 -= 1;
        p1.v.0 += 1;
    } else if p2.pos.0 < p1.pos.0 {
        p2.v.0 += 1;
        p1.v.0 -= 1;
    }

    if p2.pos.1 > p1.pos.1 {
        p2.v.1 -= 1;
        p1.v.1 += 1;
    } else if p2.pos.1 < p1.pos.1 {
        p2.v.1 += 1;
        p1.v.1 -= 1;
    }

    if p2.pos.2 > p1.pos.2 {
        p2.v.2 -= 1;
        p1.v.2 += 1;
    } else if p2.pos.2 < p1.pos.2 {
        p2.v.2 += 1;
        p1.v.2 -= 1;
    }

    return (p1, p2);
}

fn apply_gravity(planets: &mut Vec<Planet>) {
    for i in 0..planets.len() {
        for i2 in i + 1..planets.len() {
            let p1 = planets.get(i).unwrap();
            let p2 = planets.get(i2).unwrap();
            let (new_p1, new_p2) = change_planets_by_gravity(*p1, *p2);
            planets[i] = new_p1;
            planets[i2] = new_p2;
        }
    }
}

fn update_positions(planets: &mut Vec<Planet>) {
    for planet in planets.iter_mut() {
        let v = planet.v;
        planet.pos.0 += v.0;
        planet.pos.1 += v.1;
        planet.pos.2 += v.2;
    }
}

fn get_system_energy(planets: Vec<Planet>) -> isize {
    return planets.iter().map(|p| p.total_energy()).sum();
}

fn main() {
    let input = read_input_into_line_array();
    let mut planets: Vec<Planet> = parse_planets(input);

    for _ in 0..1000 {
        apply_gravity(&mut planets);
        update_positions(&mut planets);
    }

    println!("{:?}", get_system_energy(planets));
}

fn parse_planets(input: Vec<String>) -> Vec<Planet> {
    let re = Regex::new(r"<x=(.*), y=(.*), z=(.*)>").unwrap();
    return input
        .iter()
        .map(|s| {
            let groups = re.captures_iter(s).next().unwrap();
            return Planet {
                pos: (
                    groups[1].parse().unwrap(),
                    groups[2].parse().unwrap(),
                    groups[3].parse().unwrap(),
                ),
                v: (0, 0, 0),
            };
        })
        .collect();
}

fn read_input_into_line_array() -> Vec<String> {
    return io::stdin()
        .lock()
        .lines()
        .map(|res| res.ok())
        .filter(|x| x.is_some())
        .map(|x| x.unwrap())
        .collect();
}
