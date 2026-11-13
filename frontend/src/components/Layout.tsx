import { Link, NavLink, Outlet } from "react-router-dom";

const linkClass = ({ isActive }: { isActive: boolean }) =>
  [
    "text-sm",
    isActive
      ? "font-medium text-emerald-800"
      : "text-slate-600 hover:text-emerald-700",
  ].join(" ");

export function Layout() {
  return (
    <div className="min-h-screen">
      <header className="border-b border-slate-200 bg-white">
        <nav className="mx-auto flex max-w-3xl flex-wrap items-center gap-x-6 gap-y-2 px-4 py-3">
          <Link to="/" className="text-lg font-semibold text-emerald-800">
            ObservaAção
          </Link>
          <div className="flex flex-wrap gap-4">
            <NavLink to="/" end className={linkClass}>
              Início
            </NavLink>
            <NavLink to="/cidadaos" className={linkClass}>
              Cidadãos
            </NavLink>
            <NavLink to="/solicitacoes" className={linkClass}>
              Solicitações
            </NavLink>
          </div>
        </nav>
      </header>
      <main className="mx-auto max-w-3xl px-4 py-8">
        <Outlet />
      </main>
    </div>
  );
}
