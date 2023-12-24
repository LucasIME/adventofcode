import sys
from z3 import *

def to_pos_and_vel(line):
    [raw_pos, raw_vel] = line.split(" @ ")

    pos = [int(s.strip()) for s in raw_pos.split(",")]
    vel = [int(s.strip()) for s in raw_vel.split(",")]

    return (pos, vel)

def process(pos_and_velocities):
    three_lines = pos_and_velocities[:3]
    [
        [(x1, y1, z1), (vx1, vy1, vz1)],
        [(x2, y2, z2), (vx2, vy2, vz2)],
        [(x3, y3, z3), (vx3, vy3, vz3)],
    ] = three_lines

    new_x, new_y, new_z, new_vx, new_vy, new_vz = Reals('new_x new_y new_z new_vx new_vy new_vz')
    t1, t2, t3 = Reals('t1 t2 t3')
    solver = Solver()
    solver.add(
        new_x + new_vx*t1 == x1 + vx1*t1,
        new_x + new_vx*t2 == x2 + vx2*t2,
        new_x + new_vx*t3 == x3 + vx3*t3,

        new_y + new_vy*t1 == y1 + vy1*t1,
        new_y + new_vy*t2 == y2 + vy2*t2,
        new_y + new_vy*t3 == y3 + vy3*t3,

        new_z + new_vz*t1 == z1 + vz1*t1,
        new_z + new_vz*t2 == z2 + vz2*t2,
        new_z + new_vz*t3 == z3 + vz3*t3)

    if solver.check() != sat:
        raise("Not solvable system")

    model = solver.model()

    x_value = model.evaluate(new_x)
    y_value = model.evaluate(new_y)
    z_value = model.evaluate(new_z)

    sum_ref = x_value + y_value + z_value
    sum_val = model.evaluate(sum_ref)

    return sum_val

def main():
  pos_and_velocities = [ to_pos_and_vel(line_str) for line_str in sys.stdin]
  resp = process(pos_and_velocities)
  print(resp)

if __name__ == '__main__':
    main()
