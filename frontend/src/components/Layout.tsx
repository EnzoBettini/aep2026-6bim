import { Link, Outlet } from "react-router-dom";

export function Layout() {
  return (
    <div className="min-h-screen">
      <header className="border-b border-slate-200 bg-white">
        <nav className="mx-auto flex max-w-3xl items-center gap-6 px-4 py-3">
          <Link to="/" className="text-lg font-semibold text-emerald-800">
            ObservaAção
          </Link>
          <Link
            to="/"
            className="text-sm text-slate-600 hover:text-emerald-700"
          >
            Início
          </Link>
        </nav>
      </header>
      <main className="mx-auto max-w-3xl px-4 py-8">
        <Outlet />
      </main>
    </div>
  );
}
